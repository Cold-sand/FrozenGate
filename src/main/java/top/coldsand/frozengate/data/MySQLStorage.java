package top.coldsand.frozengate.data;

import top.coldsand.frozengate.data.yaml.Config;

import java.sql.*;
import java.util.UUID;


/**
 * MysqlStorage class
 * Mysql数据库服务类，继承StorageInterface接口
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class MySQLStorage implements StorageInterface{

    private final Config.SQLConfig config = Config.INSTANCE.getSQLConfig();
    private Connection connection;

    /**
     * Mysql数据库初始化
     *
     * @return 初始化是否成功
     */
    @Override
    public boolean setup(){
        try {
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(getUrl(), config.getUsername(), config.getPassword());

            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS account (" +
                    "uuid VARCHAR(36) NOT NULL," +
                    "password VARCHAR(64) NOT NULL," +
                    "PRIMARY KEY (uuid)" +
                    ") ENGINE=InnODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");

            return true;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从配置文件获取并拼接数据库连接地址
     *
     * @return 拼接后的连接地址
     */
    private String getUrl(){

        return "jdbc:mysql://" +
                config.getAddress() +
                ":" +
                config.getPort() +
                "/" +
                config.getDatabase();
    }

    public String getUserEncryptedPassword(UUID uuid){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT password FROM account WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString().replaceAll("-", ""));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String hashKeyAndSalt = resultSet.getString("password");
                resultSet.close();
                return hashKeyAndSalt;
            }else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean insertAccount(UUID uuid, String userEncryptedPassword){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO account (uuid, password) VALUES (?,?);");
            preparedStatement.setString(1,uuid.toString().replaceAll("-", ""));
            preparedStatement.setString(2, userEncryptedPassword);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public boolean updatePassword(UUID uuid, String userEncryptedPassword){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE account set password = ? where uuid=?");
            preparedStatement.setString(1, userEncryptedPassword);
            preparedStatement.setString(2, uuid.toString().replaceAll("-", ""));
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public boolean isRegistered(UUID uuid){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT uuid FROM account WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString().replaceAll("-", ""));
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean success = resultSet.next();
            resultSet.close();
            return success;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
