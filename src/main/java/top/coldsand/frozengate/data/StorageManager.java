package top.coldsand.frozengate.data;

import top.coldsand.frozengate.data.yaml.Config;


/**
 * StorageManager class
 * 有关数据库存储服务总类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class StorageManager {
    public static StorageInterface INSTANCE;

    public enum DatabaseType {
        /**
         * SQLITE数据库类型
         */
        SQLITE,
        /**
         * MYSQL数据库类型
         */
        MYSQL
    }


    public static boolean init(Config.SQLConfig config) {
        if (INSTANCE != null) {
            return false;
        }
        switch(config.getDatabaseType()) {
            case SQLITE:
                SQLiteStorage sqLiteStorage = new SQLiteStorage();
                sqLiteStorage.setup();
                INSTANCE = sqLiteStorage;
                break;
            case MYSQL:
                MySQLStorage mysqlStorage = new MySQLStorage();
                mysqlStorage.setup();
                INSTANCE = mysqlStorage;
                break;
            default:
                return false;
        }
        return true;
    }

    public static StorageInterface get() {
        if (INSTANCE == null) {
            throw new RuntimeException("StorageManager not initialized");
        }
        return INSTANCE;
    }
}
