package org.luoyifeng.wechatLife.test.csdnBlog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Discribe
 * @Auther 罗一峰[572922338@qq.com]
 * @Date 2017.12.15 10:47
 */
public class CsdnBlogDao {

    private Connection conn = null;
    private Statement stmt = null;

    public CsdnBlogDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/csdnBlog?user=root&password=123";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int add(CsdnBlog csdnBlog) {
        try {
            String sql = "INSERT INTO `webmagic`.`csdnblog` (`id`, `title`, `date`, `tags`, `category`, `view`, `comments`, `copyright`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, csdnBlog.getId());
            ps.setString(2, csdnBlog.getTitle());
            ps.setString(3, csdnBlog.getDate());
            ps.setString(4, csdnBlog.getTags());
            ps.setString(5, csdnBlog.getCategory());
            ps.setInt(6, csdnBlog.getView());
            ps.setInt(7, csdnBlog.getComments());
            ps.setInt(8, csdnBlog.getCopyright());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
