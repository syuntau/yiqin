/* 数据库 */
CREATE DATABASE `yiqin`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

/* ============================================================================================== */


/* 用户表 */
CREATE TABLE `user` (
  `id` varchar(20) NOT NULL COMMENT '用户ID',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `email` varchar(40) DEFAULT NULL COMMENT 'Email',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `company` varchar(50) DEFAULT NULL COMMENT '公司名',
  `role` tinyint(4) DEFAULT NULL COMMENT '用户类型，1：个人用户，2：企业用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `flag` tinyint(4) DEFAULT NULL COMMENT '用户状态，1：正常，2：删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 用户扩展表 */
CREATE TABLE `user_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `attribute` varchar(20) DEFAULT NULL COMMENT '用户配置属性',
  `value` varchar(500) DEFAULT NULL COMMENT '用户配置值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 分类表 */
CREATE TABLE `category` (
  `id` int(11) NOT NULL COMMENT '分类ID',
  `name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `level` int(11) DEFAULT NULL COMMENT '排序号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父分类ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 分类扩展表 */
CREATE TABLE `category_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `category_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `attribute` varchar(20) DEFAULT NULL COMMENT '分类配置属性',
  `value` varchar(100) DEFAULT NULL COMMENT '分类配置属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 品牌表 */
CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `name_en` varchar(30) DEFAULT NULL COMMENT '品牌英文名',
  `name_cn` varchar(30) DEFAULT NULL COMMENT '品牌中文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 属性表 */
CREATE TABLE `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性ID',
  `name_id` varchar(20) DEFAULT NULL COMMENT '属性标识符，例如：价格 price',
  `name` varchar(40) DEFAULT NULL COMMENT '属性名称',
  `value` varchar(100) DEFAULT NULL COMMENT '属性值（可能是包含多个值，例如：颜色 属性 的值为：黑色,红色,蓝色,白色）',
  `category_id` int(11) DEFAULT NULL COMMENT '隶属的 二级分类ID，如果设定为 0，则为各分类通用属性',
  `filter` tinyint(4) DEFAULT NULL COMMENT '是否为 二级分类 下可以筛选属性，0：不筛选，1：筛选',
  `filter_type` tinyint(4) DEFAULT NULL COMMENT '筛选类型，0：无类型，1：组合型，2：价格型，3：连续型',
  `show_value` varchar(100) DEFAULT NULL COMMENT '筛选显示值，筛选类型为1时，等同于 value 属性，筛选类型为2、3时，客户设定',
  `sort` tinyint(4) DEFAULT NULL COMMENT '是否 支持 排序，0：不排序，1：排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 产品表 */
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `product_id` varchar(20) DEFAULT NULL COMMENT '产品ID',
  `attribute_id` int(11) DEFAULT NULL COMMENT '属性ID',
  `value` varchar(100) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


/* 订单表 */
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL COMMENT '订单ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态\r\n',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `address` varchar(100) DEFAULT NULL COMMENT '收货地址',
  `mobile` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `email` varchar(40) DEFAULT NULL COMMENT '收货人Email',
  `zhifu` tinyint(4) DEFAULT NULL COMMENT '支付方式',
  `yunfei` float(9,3) DEFAULT NULL COMMENT '运费',
  `songhuoriqi` varchar(20) DEFAULT NULL COMMENT '送货日期',
  `songhuoshijian` varchar(20) DEFAULT NULL COMMENT '送货时间',
  `fapiaotaitou` varchar(50) DEFAULT NULL COMMENT '发票抬头',
  `fapiaomingxi` varchar(100) DEFAULT NULL COMMENT '发票明细',
  `product_list` varchar(1024) DEFAULT NULL COMMENT '商品列表：商品ID + 商品缩略图URL + 商品名 + 数量  + 单价 + 总价',
  `yuanjia` float(9,3) DEFAULT NULL COMMENT '订单原价',
  `zhekou` float(9,3) DEFAULT NULL COMMENT '订单折扣',
  `zongjia` float(9,3) DEFAULT NULL COMMENT '订单总价',
  `crate_date` datetime DEFAULT NULL COMMENT '订单生成时间',
  `update_date` datetime DEFAULT NULL COMMENT '订单更新时间',
  `delete_flag` tinyint(4) DEFAULT NULL COMMENT '订单删除Flag',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


/* 购物车表 */
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `use_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `product_id` varchar(20) DEFAULT NULL COMMENT '商品ID',
  `img_url` varchar(40) DEFAULT NULL COMMENT '商品图片地址',
  `price` float(9,3) DEFAULT NULL COMMENT '商品单价',
  `count` int(11) DEFAULT NULL COMMENT '商品数量',
  `product_info` varchar(200) DEFAULT NULL COMMENT '商品其他属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


/*  */


