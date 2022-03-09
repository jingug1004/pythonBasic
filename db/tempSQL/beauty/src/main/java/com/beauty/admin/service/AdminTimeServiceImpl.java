package com.beauty.admin.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.beauty.common.DateUtil;
import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductTimeSale;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.ProductTimeSaleRepository;
import com.beauty.repository.specification.ProductSpecification;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminTimeServiceImpl implements AdminTimeService {

	@Value("${ftp.host}")
	@Getter @Setter
	String ftpHost;

	@Value("${ftp.username}")
	@Getter @Setter
	String ftpUsername;

	@Value("${ftp.userpassword}")
	@Getter @Setter
	String ftpPassword;
	
	@Autowired
	private ProductTimeSaleRepository productTimeSaleRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public DataTablesResult list(DataTables input ) {
		List<ProductTimeSale> time = productTimeSaleRepository.findAll();
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(time.size());
		result.setRecordsFiltered(Long.valueOf(((Integer)time.size()).longValue()));
		result.setData(time);
		
		return result;
	}

	@Override
	public DataTablesResult getProductList(DataTables input) {
		List<Long> pIds = new ArrayList<>();
		List<ProductTimeSale> time = productTimeSaleRepository.findAll(new Sort(Direction.DESC, "regDate"));
		for(ProductTimeSale timeSale : time) {
			pIds.add(timeSale.getProduct().getProductId());
		}
		
		Specifications<Product> specs = Specifications.where(ProductSpecification.likeSearch(input.getSearch().get("value"), 0L, pIds));
		
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength()); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Product> data = productRepository.findAll(specs, pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(data.getContent().size());
		result.setRecordsFiltered(data.getTotalElements());
		result.setData(data.getContent());

		return result;
	}

	@Override
	public CommonResponse add(List<Product> timeSale, int type, int prodCount, String endDate) {
		List<ProductTimeSale> sale = new ArrayList<>();
		ProductTimeSale pts = null;
		for(Product p : timeSale) {
			pts = new ProductTimeSale();
			pts.setProduct(p);
			pts.setTimeType(type);
			pts.setProdCount(prodCount);
			pts.setRnCount(prodCount);
			if(type == 0) {
				try {
					pts.setEndDate(DateUtil.stringToDate(endDate, "yyyy-MM-dd HH:ss"));
				} catch (ParseException e) {
					return CommonResponse.of("실패 하였습니다. 다시 시도 해 주세요.", ResultCode.FAIL); 
				}
			}
			
			sale.add(pts);
		}
		
		productTimeSaleRepository.save(sale);
		
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse delete(List<ProductTimeSale> timeSale) {
		productTimeSaleRepository.delete(timeSale);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

}
