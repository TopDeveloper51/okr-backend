package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.json.JSONObject;

import com.apis.okre.entity.*;

@Mapper
public interface DashboardMapper {

//settings
    public float[] selectSettingRate(Dashboard param);

    public int[] selectSettingUserObject(Dashboard param);

    public List<User> selectSettingUserObjects(Dashboard param);

    public int[] selectSettingObjectKr(Dashboard param);

    public List<Krobject> selectSettingObjectKrs(Dashboard param);

    public int[] selectSettingObjectParent(Dashboard param);
    
    public List<Krobject> selectSettingObjectParents(Dashboard param);
    
    public List<User> selectSettingObjectNotParentUsers(Dashboard param);
    
    public int[] selectSettingkrDissolve(Dashboard param);
    
    public List<Kresult> selectSettingkrDissolves(Dashboard param);
        
    public List<User> selectSettingkrNotDissolveUsers(Dashboard param);
    
//trackings
    public int[] selectTrackConstants(Dashboard param);
    public List<Krobject> selectTrackObjectNotProgress(Dashboard param);
    public List<Kresult> selectTrackKrNotProgress(Dashboard param);
    public List<Execute> selectTrackExecuteNotProgress(Dashboard param);
    public ObjectProgress selectTrackObjectProgress(Dashboard param);
//analyze
    public List<ObjectProgress> selectAnalyzeDpObjectProgress(Dashboard param);
    public int[] selectAnalyzeScoreCounts(Dashboard param);
    public List<ObjectScore> selectAnalyzeDpObjectScore(Dashboard param);
    public List<Krobject> selectAnalyzeScoreObjects(Dashboard param);
    
//item
    public List<TaskMilestone> selectItemMsResult(Dashboard param);
    public List<TaskOwner> selectItemOwnerResult(Dashboard param);
    public int[] selectItemTaskResult(Dashboard param);

}
