package com.tgt.weekly.publication.service;

import com.tgt.weekly.publication.major.dao.BusinessOverviewDao;
import com.tgt.weekly.publication.model.BusinessOverviewDTO;
import com.tgt.weekly.publication.model.DataRateCurrentDTO;
import com.tgt.weekly.publication.model.DataRateIncrementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tgt.weekly.publication.constant.CommonConstant.*;
import static com.tgt.weekly.publication.constant.TableNameConstant.*;
import static com.tgt.weekly.publication.utils.DateUtils.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/20
 * @Time: 11:18
 * @Description: 暂时不写接口类
 **/
@Service
@Slf4j
public class BusinessOverview {

    @Autowired
    BusinessOverviewDao businessOverviewDao;

    @Autowired
    com.tgt.weekly.publication.major.dao.DataRateDao majorDataRateDao;

    @Autowired
    com.tgt.weekly.publication.flow.dao.DataRateDao flowDataRateDao;


    public BusinessOverviewDTO getOverview(){
        BusinessOverviewDTO businessOverviewDTO = BusinessOverviewDTO.builder()
                .activeTerminal(businessOverviewDao.countActiveTerminal(activeLogTableName(), getYesterday(), getToday()))
                .activeSim(businessOverviewDao.countActiveSim(activeLogTableName(), getYesterday(), getToday()))
                .onlinePeak(businessOverviewDao.countOnlinePeak(getYesterday(), getToday())).build();
        return businessOverviewDTO;
    }


    public DataRateIncrementDTO getIncrementDataRate(){
        DataRateIncrementDTO dataRateIncrementDTO = DataRateIncrementDTO.builder()
                //增量
                .eventIncrement(majorDataRateDao.countEventIncrement(activeLogTableName(), getYesterday(), getToday()))
                .deductIncrement(flowDataRateDao.countDeductIncrement(deductTableName(), getYesterday(), getToday()))
                .runIncrement(majorDataRateDao.countRunIncrement(getYesterday(), getToday()))
                .simDistributionIncrement(majorDataRateDao.countSimDistributionIncrement(getYesterday(), getToday()))
                .taskIncrement(majorDataRateDao.countTaskIncrement(getYesterday(), getToday()))
                .bindIncrement(majorDataRateDao.countBindIncrement(getYesterday(),getToday()))
                .simOperationIncrement(majorDataRateDao.countSimOperationIncrement(getYesterday(), getToday()))
                .businessIncrement(majorDataRateDao.countBusinessIncrement(getYesterday(), getToday()))
                .distributeIncrement(majorDataRateDao.countDistributeIncrement(getYesterday(), getToday()))
                .apiIncrement(majorDataRateDao.countApiIncrement(getYesterday(), getToday()))
                .infoIncrement(majorDataRateDao.countInfoIncrement(getYesterday(), getToday()))
                .alertIncrement(majorDataRateDao.countAlertIncrement(getYesterday(), getToday())).build();
        return dataRateIncrementDTO;
    }

    public DataRateCurrentDTO getCurrentDataRate(){
        DataRateCurrentDTO dataRateCurrentDTO = DataRateCurrentDTO.builder()
                //增量
                .eventCurrent(majorDataRateDao.countCurrent(ERP_EQUIPMENT_ACTIVE_LOG))
                .deductCurrent(flowDataRateDao.countCurrent(DEDUCT_FLOW_RECORD))
                .runCurrent(majorDataRateDao.countCurrent(ERP_EQUIPMENT_ACTIVE_COUNT))
                .simDistributionCurrent(majorDataRateDao.countCurrent(ERP_BIND_LOG))
                .taskCurrent(majorDataRateDao.countCurrent(ERP_TASK_RESULT))
                .bindCurrent(majorDataRateDao.countCurrent(ERP_EQU_SIM_BIND_RELEASE_LOG))
                .simOperationCurrent(majorDataRateDao.countCurrent(ERP_SIM_OPERATION_LOG))
                .businessCurrent(majorDataRateDao.countCurrent(ERP_OPERATION_LOG))
                .distributeCurrent(majorDataRateDao.countCurrent(ERP_COMMAND_LOG))
                .apiCurrent(majorDataRateDao.countCurrent(ERP_API_CALLED_LOG))
                .infoCurrent(majorDataRateDao.countCurrent(ERP_MESSAGE))
                .alertCurrent(majorDataRateDao.countCurrent(ERP_ALARM)).build();
        return dataRateCurrentDTO;
    }

    private String activeLogTableName(){
        return String.format("%s%d_%02d", ACTIVE_LOG_PREFIX, getYear(), getMonth());
    }

    private String deductTableName(){
        return String.format("%s%d_%02d", DEDUCT_FLOW_RECORD_PREFIX, getYear(), getMonth());
    }
}
