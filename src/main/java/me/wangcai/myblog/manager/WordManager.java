package me.wangcai.myblog.manager;

import me.wangcai.myblog.model.WordBean;
import me.wangcai.myblog.utils.DataBaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private final String TABLE_NAME = "words";

    private static WordManager wordManager;

    private static List<WordBean> wordList;

    private DataBaseUtil dataBaseUtil;


    public static WordManager getWordManager(){
        if(wordManager == null){
            wordManager = new WordManager();
        }
        return wordManager;
    }

    public WordManager(){
        try {
            dataBaseUtil = new DataBaseUtil(TABLE_NAME);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        init();

    }

    public void init(){
        wordList = new ArrayList<>();
        try {
            Statement stat = dataBaseUtil.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `blog`." + TABLE_NAME + "");
            while (rs.next()){
                long time = rs.getLong("time");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String imageurl = rs.getString("imageurl");
                wordList.add(0,new WordBean(title,content,imageurl,time));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void add(WordBean word) throws SQLException {
        PreparedStatement stat = dataBaseUtil.getConnection().prepareStatement("INSERT INTO `blog`.`words` (`time`, `title`, `content`, `imageurl`) VALUES (?, ?, ?, ?)");
        stat.setLong(1,word.getTime());
        stat.setString(2,word.getTitle());
        stat.setString(3,word.getContent());
        stat.setString(4,word.getImageUrl());
        stat.execute();
        stat.close();
        wordList.add(0,word);
    }

    public boolean delete(WordBean word) throws SQLException {
        boolean success = false;
        PreparedStatement stat = dataBaseUtil.getConnection().prepareStatement("DELETE FROM `blog`.`words` WHERE `time` = ?");
        stat.setLong(1,word.getTime());
        ResultSet rs = stat.executeQuery();
        if(rs.next()){
            success = true;
        }
        rs.close();
        stat.close();
        return success;
    }

    public List<WordBean> getWordList() {
        return wordList;
    }
}
