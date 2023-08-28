package top.coldsand.frozengate.data.yaml;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import top.coldsand.frozengate.data.StorageManager;


/**
 * Config class
 * 默认配置文件服务类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class Config{

    public static Config INSTANCE;
    final FileConfiguration config;

    /**
     * 默认配置文件初始化
     *
     * @param config config
     */
    public static void loadConfig(FileConfiguration config){
        INSTANCE = new Config(config);
    }
    private Config(FileConfiguration config){
        this.config = config;
    }

    public int getSaltLength() {
        return config.getInt("saltlength", 5);
    }

    /**
     * 获取指定的主城位置
     *
     * @return 管理员指定的主城位置
     */
    public Location getSpawnLocation() {
        return config.getLocation("spawnlocation");
    }

    /**
     * 获取员是否开启安全位置搜索功能布尔值
     *
     * @return 管理员是否开启安全搜索功能
     */
    public boolean getSearchSafeLocationBoolean() {return config.getBoolean("searchsafelocation", true); }

    /**
     * 获取安全搜索半径
     *
     * @return 安全搜索半径
     */
    public int getSearchRadius() {return  config.getInt("searchradius", 10); }

    /**
     * 获取数据库配置
     *
     * @return 数据库配置
     */
    public SQLConfig getSQLConfig() {
        StorageManager.DatabaseType type = StorageManager.DatabaseType.valueOf(
            config.getString("datasource.type", "SQLITE").toUpperCase()
        );

        String database = config.getString("datasource.database");
        String address = config.getString("datasource.host");
        String port = config.getString("datasource.port");


        String username = config.getString("datasource.username");
        String password = config.getString("datasource.password");

        return new SQLConfig(type,address, port, database, username, password);
    }


    public static class SQLConfig {
        final StorageManager.DatabaseType databaseType;
        final String address;
        final String port;
        final String database;
        final String username;
        final String password;

        SQLConfig(StorageManager.DatabaseType databaseType,String address, String port,String database, String username, String password) {
            this.databaseType = databaseType;
            this.address = address;
            this.port = port;
            this.database = database;
            this.username = username;
            this.password = password;
        }

        /**
         * 获取数据库连接地址
         *
         * @return 数据库连接地址
         */
        public String getAddress() {
            return address;
        }

        /**
         * 获取数据库连接端口
         *
         * @return 数据库连接端口
         */
        public String getPort() {
            return port;
        }

        /**
         * 获取数据库名
         *
         * @return 数据库名
         */
        public String getDatabase() {
            return database;
        }

        /**
         * 获取数据库用户名
         *
         * @return 数据库用户名
         */
        public String getUsername() {
            return username;
        }

        /**
         * 获取数据库用户密码
         *
         * @return 数据库用户密码
         */
        public String getPassword() {
            return password;
        }

        /**、
         * 获取数据库类型
         *
         * @return 数据库类型
         */
        public StorageManager.DatabaseType getDatabaseType() {
            return databaseType;
        }

    }
}
