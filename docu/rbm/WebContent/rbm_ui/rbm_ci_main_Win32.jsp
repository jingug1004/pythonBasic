<?xml version="1.0" encoding="euc-kr"?>
<ConnectGroup BkColor="white" Border="false" device="Win32&#32;(1024x768)" Font="Default,0" height="768" HideFrame="true" Id="SampleProject" ReSize="true" SessionURL="common::LoginSNIS.xml" Statusbar="true" Title="���ֻ�������ý���" titlebar="true" TraceMode="new" UseUpdateMenu="true" version="1.0" width="1024" work_height="535" work_width="800">
    <container Version="1000">
        <Component Height="21" Id="Spin" Image="10" Name="cySpin" Title="Spin" Version="1000" Width="121"/>
        <Component Height="21" Id="MaskEdit" Image="19" Name="cyMaskedit" Title="MaskEdit" Version="1000" Width="121"/>
        <Component Height="21" Id="Button" Image="2" Name="cyButton" Title="Button" Version="1000" Width="104"/>
        <Component Height="13" Id="Static" Image="8" Name="cyStatic" Title="Static" Version="1000" Width="40"/>
        <Component Height="21" Id="Edit" Image="3" Name="cyEdit" Title="Edit" Version="1000" Width="121"/>
        <Component Height="65" Id="Shape" Image="28" Name="cyShape" Title="Shape" Version="1000" Width="65"/>
        <Component Height="65" Id="Pie" Image="30" Name="cyPie" Title="Pie" Version="1000" Width="65"/>
        <Component Height="21" Id="Checkbox" Image="4" Name="cyCheckBox" Title="Check&#32;Box" Version="1000" Width="97"/>
        <Component Height="24" Id="FileDialog" Image="18" Name="cyFileDialog" Title="File&#32;Dialog" Version="1000" Width="24"/>
        <Component Height="24" Id="File" Image="25" Name="cyFile" Title="File" Version="1000" Width="24"/>
        <Component Height="97" Id="List" Image="6" Name="cyListBox" Title="List" Version="1000" Width="145"/>
        <Component Height="21" Id="Combo" Image="5" Name="cyComboEx" Title="ComboBox" Version="1000" Width="145"/>
        <Component Height="21" Id="Radio" Image="7" Name="cyRadio" Title="Radio" Version="1000" Width="113"/>
        <Component Height="72" Id="TextArea" Image="16" Name="cyTextArea" Title="Text&#32;Area" Version="1000" Width="185"/>
        <Component Height="17" Id="Progressbar" Image="31" Name="cyProgressbar" Title="Progressbar" Version="1000" Width="150"/>
        <Component Height="97" Id="TreeView" Image="11" Module="CyTreeView" Name="cyTreeView" Title="Tree&#32;View" Version="1000" Width="121"/>
        <Component Height="120" Id="Grid" Image="1" Module="CyGrid" Name="cyGrid" Title="Grid" Version="1000" Width="320"/>
        <Component Height="105" Id="Image" Image="9" Name="cyImage" Title="Image" Version="1000" Width="105"/>
        <Component Height="21" Id="Calendar" Image="13" Module="CyCalendarEx" Name="cyCalendarEx" Title="Calendar" Version="1000" Width="145"/>
        <Component Height="193" Id="Div" Image="20" Name="cyDiv" Title="Division" Version="1000" Width="289"/>
        <Component Id="PopupDiv" Image="54" Name="CyPopupDiv" Title="Popup&#32;Div" Version="1000"/>
        <Component Height="193" Id="Tab" Image="12" Name="cyTab" Title="Tab" Version="1000" Width="289"/>
        <Component Height="260" Id="WebBrowser" Image="24" Module="CyWebBrowser" Name="cyWebBrowser" Title="WebBrowser" Version="1000" Width="364"/>
        <Component Height="168" Id="swFlash" Image="27" Module="CySWFlash" Name="cySWFlash" Title="Flash" Version="1000" Width="248"/>
        <Component Height="24" Id="MenuBar" Image="32" Module="CyMenuBar" Name="cyMenuBar" Title="MenuBar" Version="1000" Width="192"/>
        <Component Height="50" Id="Split" Image="55" Module="CySplit" Name="cySplit" Title="Split" Version="1000" Width="8"/>
        <Component Height="100" Id="TeeChart" Image="23" Module="CyTeeChart" Name="CyTeeChart" Title="TeeChart" Version="1000" Width="100"/>
        <Component Height="15" Id="HttpFile" Image="24" Name="CyHttpFile" Title="CyHttpFile" Version="1000" Width="15"/>
        <Component Height="15" Id="ftp" Image="51" Module="CyFtpEx" Name="CyFtpEx" Title="FTP" Version="1000" Width="15"/>
        <Component Height="50" Id="AxComm" Image="106" Module="CyAxComm" Name="cyAxComm" Title="AxComm" Version="1000" Width="50"/>
        <Component Height="100" Id="CyAxChartFx" Image="23" Module="CyAxChartFxU" Name="CyAxChartFx" Title="CyAxChartFx" Version="1000" Width="100"/>
        <Component Height="100" Id="ext" Image="23" Module="ExtCommonApi" Name="ExtCommonApi" Title="ext" Version="1000" Width="100"/>
        <Component Height="100" Id="ubi" Image="23" Module="CyAxUbiReport" Name="CyAxUbiReport" Title="ubi" Version="1000" Width="100"/>
    </container>
    <protocols version="1000">
        <protocol id="file" name="cyHttpAdp" Retry="0" TimeOut="1200" Version="1000"/>
        <protocol Compress="True" CompressMethod="ZLIB" id="http" name="cyHttpAdp" Retry="0" TimeOut="1200" Version="1000" XmlFormat="False"/>
        <protocol id="https" name="cyHttpAdp" Retry="0" TimeOut="1200" Version="1000"/>
    </protocols>
    <AppGroups>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="common" Type="form" Version="1.0">
            <script Baseurl="./common/" CacheLevel="none" Ext="xml" ScriptUrl="./common/"/>
        </AppGroup>
        <AppGroup CodePage="euc-kr" Language="0" Prefix="lib" Type="js" Version="1.0">
            <script Baseurl="./script/" CacheLevel="none" Ext="js"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="img" Type="file" Version="1000">
            <Script Baseurl="./image/common/" CacheLevel="none"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rem" Type="form" Version="1.0">
            <Script Baseurl="./form/rem/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rem/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rbo" Type="form" Version="1.0">
            <Script Baseurl="./form/rbo/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rbo/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rbs" Type="form" Version="1.0">
            <Script Baseurl="./form/rbs/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rbs/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rbr" Type="form" Version="1.0">
            <Script Baseurl="./form/rbr/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rbr/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rsy" Type="form" Version="1.0">
            <Script Baseurl="./form/rsy/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rsy/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rsm" Type="form" Version="1.0">
            <Script Baseurl="./form/rsm/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rsm/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="frm_rev" Type="form" Version="1.0">
            <Script Baseurl="./form/rev/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rev/"/>
        </AppGroup>
        <AppGroup CacheLevel="none" CodePage="euc-kr" Prefix="frm_rbb" Type="form" Version="1.0">
            <Script Baseurl="./form/rbb/" CacheLevel="none" Ext="xml" ScriptUrl="./form/rbb/"/>
        </AppGroup>
        <DataGroup Baseurl="http://localhost:8080/rbm/" CacheLevel="none" CodePage="euc-kr" Language="0" Prefix="DEFAULT_SERVER" Streamver="4000" TrMethod="normal" Type="JSP" Version="1000"/>
    </AppGroups>
    <Variables>
        <Var Comment="�򰡹޴�&#32;�����&#32;���&#32;����&#32;URL(width&#32;:&#32;104&#32;/&#32;height&#32;:&#32;104)" Id="GBL_EVAL_PHOTO_URL" Type="Global" Value="http://ifis.kspo.or.kr:8090/snis/files/photo/hum/id/jong_persl/"/>
        <Var Id="GBL_FILE_UPDNLOAD_URL" Type="Global" Value="http://localhost:8080/rbm/fileHandle"/>
        <Var Id="GBL_IMAGELINK_URL" Type="Global" Value="http://localhost:8080/rbm/upload_image/"/>
        <Var Comment="���������" Id="GBL_MEET_CD" Type="Global" Value="001"/>
        <Var Id="GBL_MENUID" Type="Global"/>
        <Var Comment="��������&#32;�̹���&#32;URL" Id="GBL_PHOTO_URL" Type="Global" Value="http://www.kcycle.or.kr/h_image/"/>
        <Var Comment="����Ʈ����&#32;URL" Id="GBL_REPORT_URL" Type="Global" Value="http://localhost:8080/rbm/report/"/>
        <Var Id="GBL_RET_VALUE" Type="Global"/>
        <Var Id="GBL_SERVER_UPLOAD_DIR" Type="Global" Value="http://localhost:8080/rbm/upload_file/"/>
        <Var Id="GBL_SERVER_URL" Type="Global" Value="http://localhost:8080/rbm/"/>
        <Var Comment="����ID" Id="GBL_SESSIONID" Type="Global"/>
        <Var Comment="������&#32;�������&#32;����" Id="GBL_SESSIONOFF" Type="Global"/>
        <Var Id="GBL_LOGOUT_GBN" Type="Global"/>
    </Variables>
    <DockBars>
        <DockBar AddMenu="false" Align="top" Border="false" Bottom="76" Fixed="false" ID="FrameTop" InitURL="common::FrameTop.xml" Top="0"/>
		<DockBar AddMenu="false" Align="bottom" Border="false" Bottom="950" Fixed="false" ID="FrameBottom" InitURL="common::FrameBottom.xml" Top="928"/>
		<DockBar AddMenu="false" Align="left" Border="false" Bottom="950" Fixed="false" ID="FrameRight" InitURL="common::FrameRight.xml" Right="200" Top="76"/>
    </DockBars>
    <Datasets>
        <Dataset DataSetType="Dataset" Id="dsOpenFormList" OnRowPosChanged="fnc_FormTotalCount">
            <Contents>
                <colinfo id="FORM_NAME" size="256" type="STRING"/>
                <colinfo id="FORM_CAPTION" size="256" type="STRING"/>
            </Contents>
        </Dataset>
        <Dataset DataSetType="Dataset" Id="gdsMenu">
            <Contents>
                <colinfo id="MN_ID" size="256" type="STRING"/>
                <colinfo id="MN_NM" size="256" type="STRING"/>
                <colinfo id="ORD_NO" size="256" type="STRING"/>
                <colinfo id="UP_NM_ID" size="256" type="STRING"/>
                <colinfo id="SCRN_ID" size="256" type="STRING"/>
                <colinfo id="URL" size="256" type="STRING"/>
                <colinfo id="RMK" size="256" type="STRING"/>
                <colinfo id="SRCH_YN" size="256" type="STRING"/>
                <colinfo id="INPT_YN" size="256" type="STRING"/>
                <colinfo id="APRO_YN" size="256" type="STRING"/>
                <colinfo id="LVL" size="256" type="STRING"/>
                <colinfo id="TOP_MENU_ID" size="256" type="STRING"/>
            </Contents>
        </Dataset>
        <Dataset DataSetType="Dataset" Id="gdsSelectItem"></Dataset>
        <Dataset DataSetType="Dataset" Id="gdsSysdate"></Dataset>
        <Dataset DataSetType="Dataset" Id="gdsUser">
            <Contents>
                <colinfo id="USER_ID" size="256" type="STRING"/>
                <colinfo id="USER_NM" size="256" type="STRING"/>
                <colinfo id="DEPT_CD" size="256" type="STRING"/>
                <colinfo id="PSWD" size="256" type="STRING"/>
                <colinfo id="ASSO_CD" size="256" type="STRING"/>
                <colinfo id="FLOC" size="256" type="STRING"/>
                <colinfo id="STAT" size="256" type="STRING"/>
                <colinfo id="TEL_NO" size="256" type="STRING"/>
                <colinfo id="FGRADE" size="256" type="STRING"/>
                <colinfo id="TEAM_CD" size="256" type="STRING"/>
                <colinfo id="DEPT_NM" size="256" type="STRING"/>
                <colinfo id="TEAM_NM" size="256" type="STRING"/>
                <colinfo id="BRNC_CD" size="256" type="STRING"/>
                <colinfo id="HP_NO" size="256" type="STRING"/>
                <colinfo id="TEAM_DETL_CD" size="256" type="STRING"/>
                <colinfo id="AUTH_TEAM_CD" size="256" type="STRING"/>
            </Contents>
        </Dataset>
        <Dataset DataSetType="Dataset" Id="gdsPopup"></Dataset>
        <Dataset DataSetType="Dataset" Id="gdsTopMenu">
            <Contents>
                <colinfo id="MN_ID" size="256" type="STRING"/>
                <colinfo id="MN_NM" size="256" type="STRING"/>
                <colinfo id="ORD_NO" size="256" type="STRING"/>
                <colinfo id="UP_NM_ID" size="256" type="STRING"/>
                <colinfo id="SCRN_ID" size="256" type="STRING"/>
                <colinfo id="URL" size="256" type="STRING"/>
                <colinfo id="RMK" size="256" type="STRING"/>
                <colinfo id="SRCH_YN" size="256" type="STRING"/>
                <colinfo id="INPT_YN" size="256" type="STRING"/>
                <colinfo id="APRO_YN" size="256" type="STRING"/>
                <colinfo id="LVL" size="256" type="STRING"/>
            </Contents>
        </Dataset>
        <Dataset DataSetType="Dataset" Id="gdsAlarmMng"></Dataset>
        <Dataset DataSetType="Dataset" Id="gdsAlarmInfo">
            <Contents>
                <colinfo id="SEND_USER_ID" size="256" summ="default" type="STRING"/>
                <colinfo id="SEND_USER_NM" size="256" summ="default" type="STRING"/>
                <colinfo id="SEND_USER_PHONE" size="256" summ="default" type="STRING"/>
                <colinfo id="TITLE" size="256" summ="default" type="STRING"/>
                <colinfo id="DESC" size="256" summ="default" type="STRING"/>
                <colinfo id="LINK_MN_ID" size="256" summ="default" type="STRING"/>
            </Contents>
        </Dataset>
    </Datasets>
    <Script></Script>
    <Resource CacheLevel="none" Ext="res" Url="./image/Image.res" Version="1000"/>
    <GlobalStyle>
        <style BorderColor="#eeeeee" FixedFont="����,9" ID="snis_grid" TextColor="#333333"/>
        <style BorderColor="#d0c7d8" DisablebkColor="user61" DisableColor="user5" Font="����,9" Formcolor="#f7f7ff" ID="snis_if_input01" TextColor="user5"/>
        <style BorderColor="user6" DisablebkColor="#daede8" DisableColor="#777777" FixedFont="����,9" Font="����,9" Formcolor="user0" ID="snis_if_lable" TextColor="user5"/>
        <style DisablebkColor="#daede8" DisableColor="#777777" Formcolor="#daede8" ID="snis_if_radio" TextColor="user5"/>
        <style BorderColor="#d0c7d8" DisablebkColor="user0" DisableColor="user5" Formcolor="#f7f7ff" ID="snis_m_input01" TextColor="user5"/>
        <style BorderColor="#dddddd" DisablebkColor="user0" FixedFont="����,9" Formcolor="user9" ID="snis_m_lable" TextColor="user4"/>
        <style ID="snis_m_radio" TextColor="user5"/>
        <style FixedFont="����,9,Bold" Font="����,9,Bold" ID="snis_m_stitle" TextColor="user4"/>
        <style FixedFont="����ü,9,Bold" Font="����,9,Bold" ID="snis_m_title" TextColor="user21"/>
        <style Font="����,9" ID="snis_txt" TextColor="user5"/>
    </GlobalStyle>
    <MDIInfo>
        <Forms OpenStyle="Max">
            <Form ID="MDIForm" initURL="form::comMain.xml" OpenStyle="Max"/>
        </Forms>
        <Shortcuts color="default" font="tahoma,9" HeaderTitle="Title:Description:Status">
            <Shortcut Id="Shortcut0"/>
        </Shortcuts>
    </MDIInfo>
    <UserColor User0="#f7f8fa" User1="#80a3a1" User10="#f6ebe5" User11="lemonchiffon" User12="#10a603" User15="white" User16="#fbfbfb" User17="#e7f2f6" User18="#bfbfbf" User19="#333333" User2="white" User20="#f4f4f4" User21="#666666" User22="#dddddd" User25="#3e3d3d" User3="#bfbfbf" User30="#b7cbe1" User35="#b3bdc5" User4="#666666" User40="#4ea6d1" User41="white" User5="#313131" User50="#00a9e9" User51="#dfe0e0" User52="#959595" User58="#0f3252" User59="#fdfdfd" User6="#cccccc" User60="#21488a" User61="#e0e0e0" User62="#f3f3f3" User63="#739dd7" User7="#f0f3f9" User8="#b7cbe1" User9="#fcfcfc"/>
    <ActiveX Version="1000">
        <Component Id="AxMsie" Image="24" Progid="Shell.Explorer" Title="Microsoft" Version="1000"/>
        <Component Height="25" Id="pBranchSosfo" Image="106" Progid="pBranchSosfo.IpBranchSosfo" Title="pBranchSosfo" Version="1000" Width="25"/>
        <Component Id="Winsock" Image="23" Progid="MSWinsock.Winsock" Title="Microsoft" Version="1000"/>
        <Component Height="400" Id="Windows" Image="54" Progid="WMPlayer.OCX" Title="Windows" Version="1000" Width="300"/>
    </ActiveX>
    <DataObjects></DataObjects>
    <ServiceObjects></ServiceObjects>
    <EXTAPIS>
        <EXTAPI Name="ExtCommonApi" Version="1000"/>
    </EXTAPIS>
</ConnectGroup>