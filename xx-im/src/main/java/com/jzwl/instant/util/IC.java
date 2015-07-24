package com.jzwl.instant.util;

/**
 * InstantConstant
 * 
 * @author xx
 * 
 */
public class IC {

	// public static final String redis_root = "im.dev.";
	// public static String server_connect_address = "192.168.1.200:12356";

	public static final String redis_root = "im.";
	public static String server_connect_address = "192.168.1.12:12356";//

	public static final String redisCode = "utf-8";

	public static final int maxidle = 30000;// 最大空闲30s

	public static final String systemUid = "system";// 系统uid
	public static final String serviceSid = "1437557156306";// 系统服务号sid
	// 客户端连接地址·

	// redis接收消息 队列 key
	public static final String recieve_message_queus_key = redis_root
			+ "rev.queue";

	// redis发送到消息 队列 key
	public static final String send_message_queus_key = redis_root
			+ "sed.queue";

	// redis 发送失败key
	public static final String send_faile_times_key = redis_root + "sed.faile";

	// redis 用户信息key
	public static final String user_simple_info_key = redis_root + "user.info";

	// redis 摇一摇key
	public static final String user_rocking_key = redis_root + "user.rocking";

	// redis 创建群key
	public static final String user_create_group_key = redis_root
			+ "create.group";

	// mongo传输的消息
	public static final String mongodb_message = "message";

	// mongo传输失败的消息
	public static final String mongodb_leavel_message = "message_leavel";

	// mongo传输的文件
	public static final String mongodb_fileinfo = "message_fileinfo";

	// mongo用户信息
	public static final String mongodb_userinfo = "user_info";

	// mongo群信息
	public static final String mongodb_groupinfo = "group_info";

	// mongo动态
	public static final String mongodb_dynamic = "dynamic";

	// mongo动态评论
	public static final String mongodb_dynamic_comment = "dynamic_comment";

	// mongo服务号
	public static final String mongodb_service = "service";

	/**
	 * 上下线通知
	 */
	public static final boolean ON_OFF_LINE_BROCAST = true;

	/**
	 * 登录
	 */
	public static final String LOGIN = "login";

	/**
	 * 单聊
	 */
	public static final String CHAT = "chat";

	/**
	 * 群聊
	 */
	public static final String GROUP_CHAT = "groupchat";

	/**
	 * 服务号
	 */
	public static final String SERVICE_CHAT = "servicechat";

	/**
	 * ping pong
	 */
	public static final String PING = "ping";

	/**
	 * 系统消息
	 */
	public static final String SYS = "system";

	/**
	 * 添加好友
	 */
	public static final String ACTION_ADD_FRIEND = "addfriend";

	/**
	 * 删除好友
	 */
	public static final String ACTION_DEL_FRIEND = "delfriend";

	/**
	 * 同意好友申请
	 */
	public static final String ACTION_AGREE_FRIEND = "agreefriend";

	/**
	 * 不同意好友申请
	 */
	public static final String ACTION_NOT_AGREE_FRIEND = "notagreefriend";

	/**
	 * 申请入群
	 */
	public static final String ACTION_APPLY_GROUP = "applygroup";

	/**
	 * 同意入群
	 */
	public static final String ACTION_AGREE_GROUP = "agreegroup";

	/**
	 * 不同意入群
	 */
	public static final String ACTION_NOT_AGREE_GROUP = "notagreegroup";

	/**
	 * 系统消息
	 */
	public static final String ERROR = "error";

	/**
	 * 重新登录
	 */
	public static final String RELOGIN = "relogin";

	/**
	 * 退出
	 */
	public static final String LOGIN_OUT = "loginout";

	/**
	 * 对方收到消息后回声
	 */
	public static final String CHAT_REC_ECHO = "chat_recieve_echo";

	/**
	 * 消息发送成功后回声
	 */
	public static final String CHAT_SEND_ECHO = "chat_send_echo";

	/**
	 * 添加好友 同意添加 认证后 加入协会 的通知
	 */
	public static final String ECHO = "echo";

	/**
	 * 系统推送（系统消息）
	 */
	public static final String BROCAST = "brocast";

	/**
	 * 测试是否能够与服务器连接
	 */
	public static final String TEST = "test";

	/** 0=开发测试环境 1=正式环境 */
	public static final int redis_switch = 1;

	/** 10 秒 */
	public static final int SEC_10 = 10 * redis_switch;

	/** 1 分钟 */
	public static final int MIN_1 = SEC_10 * 6;

	/** 1小时 */
	public static final int HOU_1 = MIN_1 * 60;

	/** 1天 */
	public static final int DAY_1 = HOU_1 * 24;

	/** 1周 */
	public static final int WEEK_1 = DAY_1 * 7;

	/** 1月 */
	public static final int MOU_1 = DAY_1 * 30;

	/** redis控制开关 */
	public static final int REDIS_CACHE_CONTROL = 0;

}
