package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.common.FtpClientUtil;
import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.MainMenu;
import com.beauty.entity.MainSecondBanner;
import com.beauty.entity.MainTenMenu;
import com.beauty.entity.Product;
import com.beauty.repository.MainFirstBannerRepository;
import com.beauty.repository.MainMenuRepository;
import com.beauty.repository.MainSecondBannerRepository;
import com.beauty.repository.MainTenMenuRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.MainResponse;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class MainServiceImpl implements MainService {

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
	private ProductRepository productRepository;
	
	@Autowired
	private MainFirstBannerRepository mainFirstBannerRepository;
	
	@Autowired
	private MainSecondBannerRepository mainSecondBannerRepository;
	
	@Autowired
	private MainTenMenuRepository mainTenMenuRepository;
	
	@Autowired
	private MainMenuRepository mainMenuRepository;
	
	
	@Override
	public MainResponse getMain() {
		List<MainFirstBanner> firstList = mainFirstBannerRepository.findByShowTypeAndShowYn(1, "Y");
		
		List<MainSecondBanner> secondList = mainSecondBannerRepository.findByShowYn("Y");
		
		List<MainTenMenu> tenList = mainTenMenuRepository.findByShowYn("Y");
		
		List<MainMenu> mainList = mainMenuRepository.findByShowYn("Y");
		List<Product> productList = productRepository.findTop50ByStopSellingOrderByScoreDesc(0);
		
		return MainResponse.of("success", ResultCode.SUCCESS, firstList, secondList, tenList, mainList, productList);
	}


	@Override
	public CommonResponse uploadFile(MultipartFile uploadFile, String type) {
		String ftpDirectory = "";
		if(type.equals("P")) {
			ftpDirectory = BeautyConstants.FTP_USER;
		} else if(type.equals("R")) {
			ftpDirectory = BeautyConstants.FTP_REVIEW;
		} else if(type.equals("Q")) {
			ftpDirectory = BeautyConstants.FTP_QNA;
		}
		ftpDirectory = ftpDirectory + "/" + DateUtil.getDateString("yyyyMMdd");
		String filePath = uploadFile(uploadFile, ftpDirectory, type);
		return CommonResponse.of(filePath, ResultCode.SUCCESS);
	
	}

	
	public String uploadFile( MultipartFile localDirectoryAndFileName, String ftpDirectory, String sub_name) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, sub_name);
				f.close();
				f = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

}
