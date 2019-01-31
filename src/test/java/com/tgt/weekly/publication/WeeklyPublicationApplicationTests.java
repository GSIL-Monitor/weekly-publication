package com.tgt.weekly.publication;

import com.alibaba.fastjson.JSON;
import com.tgt.weekly.publication.model.BusinessOverviewDTO;
import com.tgt.weekly.publication.model.DataRateCurrentDTO;
import com.tgt.weekly.publication.model.DataRateIncrementDTO;
import com.tgt.weekly.publication.properties.DMProperties;
import com.tgt.weekly.publication.service.BusinessOverview;
import com.tgt.weekly.publication.service.ReportServiceImpl;
import com.tgt.weekly.publication.service.SendMailImpl;
import com.tgt.weekly.publication.ssh2.LinuxConnectImpl;
import com.tgt.weekly.publication.utils.FreemarkerUtil;
import freemarker.template.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class WeeklyPublicationApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Qualifier("flowDataSource")
	@Autowired
	DataSource dataSource;

	@Autowired
	DMProperties dmProperties;

	@Autowired
	LinuxConnectImpl linuxConnect;

	@Autowired
	BusinessOverview businessOverview;

	@Autowired
	ReportServiceImpl reportService;

	@Autowired
	SendMailImpl sendMail;

	@Test
	public void contextLoads() {
		jdbcTemplate.setDataSource(dataSource);
		jdbcTemplate.query("SELECT * FROM fixdpi_app_info", new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
			}
		});
	}

	@Test
	public void dmPropertiesGet(){
		Assert.assertTrue(dmProperties.getDmList().size() == 3);
		System.out.println(JSON.toJSONString(dmProperties.getDmList()));
	}

	@Test
	public void dmCombine(){
		long start = System.currentTimeMillis();
		linuxConnect.combineData();
		linuxConnect.writeFile();
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void xml(){
		long start = System.currentTimeMillis();
		linuxConnect.combineData();
		linuxConnect.writeFile();
		Map root = reportService.handleCombineData();
		root.put("statisticalTime", "2018-12-20");
		new FreemarkerUtil().createWordTemplate(root, "bss-daily-2003.xml", "F:\\bss-daily","BSS服务器运行状态日报-demo.doc");
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void testOverview(){
		BusinessOverviewDTO businessOverviewDTO = businessOverview.getOverview();
		System.out.println(businessOverviewDTO);
	}

	@Test
	public void testRateIncrement(){
		DataRateIncrementDTO dataRateIncrementDTO = businessOverview.getIncrementDataRate();
		System.out.println(dataRateIncrementDTO);
	}

    @Test
    public void testRateCurrent(){
        DataRateCurrentDTO dataRateCurrentDTO = businessOverview.getCurrentDataRate();
        System.out.println(dataRateCurrentDTO);
    }

	@Test
	public void testDetails(){
		long start = System.currentTimeMillis();
		System.out.println(JSON.toJSONString(reportService.handleCombineData()));
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void testEmails(){
		sendMail.sendDayPublication();
	}

}

