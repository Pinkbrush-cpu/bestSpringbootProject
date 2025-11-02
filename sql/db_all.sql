-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: springboot
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `class_name` varchar(100) NOT NULL,
  `class_code` varchar(100) NOT NULL,
  `teacher_id` int NOT NULL,
  `admission_year` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `student_max_count` int NOT NULL,
  `class_state` varchar(20) NOT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (1,'计算机科学与技术一班','23141101',2,'2023',50,'启用'),(3,'2020级二班','202002',2,'2020',45,'启用'),(4,'2020级三班','202003',2,'2020',45,'启用'),(5,'2020级四班','202004',2,'2020',45,'启用'),(6,'2020级五班','202005',2,'2020',45,'启用'),(7,'2020级六班','202006',2,'2020',45,'启用'),(8,'2020级七班','202007',2,'2020',45,'启用'),(9,'2020级八班','202008',2,'2020',45,'启用'),(10,'2020级九班','202009',2,'2020',45,'启用'),(11,'2020级十班','202010',2,'2020',45,'启用'),(12,'2020级十一班','202011',2,'2020',45,'启用'),(13,'2020级十二班','202012',2,'2020',45,'启用'),(14,'2020级十三班','202013',2,'2020',45,'启用'),(15,'2020级十四班','202014',2,'2020',45,'启用'),(16,'2020级十五班','202015',2,'2020',45,'启用'),(17,'2020级十六班','202016',2,'2020',45,'启用'),(18,'2020级十七班','202017',2,'2020',45,'启用'),(19,'2020级十八班','202018',2,'2020',45,'启用'),(20,'2020级十九班','202019',2,'2020',45,'启用'),(21,'2020级二十班','202020',2,'2020',45,'启用'),(22,'2021级一班','202101',2,'2021',45,'启用'),(23,'2021级二班','202102',2,'2021',45,'启用'),(24,'2021级三班','202103',2,'2021',45,'启用'),(25,'2021级四班','202104',2,'2021',45,'启用'),(26,'2021级五班','202105',2,'2021',45,'启用'),(27,'2021级六班','202106',2,'2021',45,'启用'),(28,'2021级七班','202107',2,'2021',45,'启用'),(29,'2021级八班','202108',2,'2021',45,'启用'),(30,'2021级九班','202109',2,'2021',45,'启用'),(31,'2021级十班','202110',2,'2021',45,'启用'),(32,'2021级十一班','202111',2,'2021',45,'启用'),(33,'2021级十二班','202112',2,'2021',45,'启用'),(34,'2021级十三班','202113',2,'2021',45,'启用'),(35,'2021级十四班','202114',2,'2021',45,'启用'),(36,'2021级十五班','202115',2,'2021',45,'启用'),(37,'2021级十六班','202116',2,'2021',45,'启用'),(38,'2021级十七班','202117',2,'2021',45,'启用'),(39,'2021级十八班','202118',2,'2021',45,'启用'),(40,'2021级十九班','202119',2,'2021',45,'启用'),(41,'2021级二十班','202120',2,'2021',45,'启用'),(43,'2020级二班','202002',2,'2020',45,'启用'),(44,'2020级三班','202003',2,'2020',45,'启用'),(45,'2020级四班','202004',2,'2020',45,'启用'),(46,'2020级五班','202005',2,'2020',45,'启用'),(47,'2020级六班','202006',2,'2020',45,'启用'),(48,'2020级七班','202007',2,'2020',45,'启用'),(49,'2020级八班','202008',2,'2020',45,'启用'),(50,'2020级九班','202009',2,'2020',45,'启用'),(51,'2020级十班','202010',2,'2020',45,'启用'),(52,'2020级十一班','202011',2,'2020',45,'启用'),(53,'2020级十二班','202012',2,'2020',45,'启用'),(54,'2020级十三班','202013',2,'2020',45,'启用'),(55,'2020级十四班','202014',2,'2020',45,'启用'),(56,'2020级十五班','202015',2,'2020',45,'启用'),(57,'2020级十六班','202016',2,'2020',45,'启用'),(58,'2020级十七班','202017',2,'2020',45,'启用'),(59,'2020级十八班','202018',2,'2020',45,'启用'),(60,'2020级十九班','202019',2,'2020',45,'启用'),(61,'2020级二十班','202020',2,'2020',45,'启用'),(62,'2021级一班','202101',2,'2021',45,'启用'),(63,'2021级二班','202102',2,'2021',45,'启用'),(64,'2021级三班','202103',2,'2021',45,'启用'),(65,'2021级四班','202104',2,'2021',45,'启用'),(66,'2021级五班','202105',2,'2021',45,'启用'),(67,'2021级六班','202106',2,'2021',45,'启用'),(68,'2021级七班','202107',2,'2021',45,'启用'),(69,'2021级八班','202108',2,'2021',45,'启用'),(70,'2021级九班','202109',2,'2021',45,'启用'),(71,'2021级十班','202110',2,'2021',45,'启用'),(72,'2021级十一班','202111',2,'2021',45,'启用'),(73,'2021级十二班','202112',2,'2021',45,'启用'),(74,'2021级十三班','202113',2,'2021',45,'启用'),(75,'2021级十四班','202114',2,'2021',45,'启用'),(76,'2021级十五班','202115',2,'2021',45,'启用'),(77,'2021级十六班','202116',2,'2021',45,'启用'),(78,'2021级十七班','202117',2,'2021',45,'启用'),(79,'2021级十八班','202118',2,'2021',45,'启用'),(80,'2021级十九班','202119',2,'2021',45,'启用'),(81,'2021级二十班','202120',2,'2021',45,'启用'),(82,'航空一班','23161101',32,'2024',14,'启用'),(83,'111','111',31,'2025',161,'禁用'),(84,'222','222',2,'2025',110,'已毕业'),(85,'asd','asd',2,'2025',0,'启用'),(86,'333','333',2,'2025',3,'启用');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clazz_student`
--

DROP TABLE IF EXISTS `clazz_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz_student` (
  `cla_stu_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `clazz_id` int NOT NULL,
  `student_code` varchar(50) not null,
  `state` int not null ,
  PRIMARY KEY (`cla_stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz_student`
--

LOCK TABLES `clazz_student` WRITE;
/*!40000 ALTER TABLE `clazz_student` DISABLE KEYS */;
INSERT INTO `clazz_student` VALUES (1,6,1);
/*!40000 ALTER TABLE `clazz_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `create_id` int NOT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT '未发布',
  `questionIds` varchar(255) NOT NULL,
  `totalScore` int NOT NULL DEFAULT '0',
  `totalTitles` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`exam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (9,31,'123','已发布','[36,46,47,40,37,36,45,46,47,40,37,36,45,46,47,40,37,36,45,46,47,46,47,40,37,36,45,46,46,47,40,37,36,45,46,47,40,46,47,40,37,36,45,46,47,40,37,36,45,37,36,45,47,40,37,36,45,40,37,36,45,43,38,40]',10,2),(10,31,'asd','已发布','[38,40,37]',15,3),(17,31,'测试','已发布','[38,40,37,45,50,36,43,46,39,47]',50,0),(18,31,'无锡好','已发布','[40,45,37,50,36,43]',30,0),(19,31,'asd','已发布','[38,40,37,45]',20,0);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `n_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `create_mid` bigint NOT NULL COMMENT '创建管理员ID',
  `title` varchar(255) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容',
  `target` varchar(20) NOT NULL COMMENT '目标对象 teacher / student / all',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`n_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,1,'测试','这是一封测试信','1','2025-09-21 15:28:09');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `q_id` int NOT NULL AUTO_INCREMENT,
  `create_id` int NOT NULL,
  `type` varchar(255) NOT NULL,
  `title` text NOT NULL,
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `answer_text` text,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `score` int NOT NULL,
  PRIMARY KEY (`q_id`),
  KEY `id` (`create_id`),
  CONSTRAINT `id` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,11,'判断题','ad','[是, 不是]','123','是',10),(36,31,'多选题','已知函数 f(x)=x3 −3 x2+4，则下列说法正确的是（ ）','[\"函数 f(x) 在 x=2 处取得极小值\",\"函数 f(x) 在区间 (−∞,0) 上单调递增\",\"函数 f(x) 的图像关于点 (1,2) 对称\",\"函数 f(x) 在区间 [0,3] 上的最大值为 4\"]','函数：\nf(x)=x3− 3 x2+4\nA. 极值点判断：\n求导：\nf ′(x)=3 x2\n −6 x= 3 x (x − 2)\n令 f ′(x)=0⇒x=0 或 x=2\n再求二阶导：\nf ′′(x)=6x−6\nf ′′(0)=−6<0⇒x=0 是极大值点\nf ′′(2)=6>0⇒x=2 是极小值点','A,C',5),(37,31,'单选题','已知等差数列 {an} 的前 n 项和为 Sn，若 a1 =3，S5 =35，则 a6 =（ ）','[\"会a\",\"不会\"]','利用高中知识就可以答出来，不难','B',5),(38,31,'判断题','是否存在一个使1+1等于3的数字','[\"会\",\"不会\"]','唐人才选存在','B',5),(39,31,'简答题','回答费马定理的实现原理','null','不是你以为我真会啊','略',5),(40,31,'判断题','如果我在判断题写个一，我会把这个题放到题库里吗？','[\"会\",\"不会\"]','不是你以为我真会啊','A',5),(43,31,'多选题','如果我在判断题写个一，我会把这个题放到题库里吗？','[\"会\",\"不会\"]','不是你以为我真会啊','B,A',5),(45,31,'单选题','Java 运行时环境（JRE）默认使用的是 IPv6 协议栈，但并不是所有的数据库服务器和网络配置都完全支持 IPv6。某些数据库（特别是在某些企业环境中）只支持 IPv4 或者对 IPv6 的支持不稳定。因此，JVM 在尝试连接数据库时，可能会因为 IPv6 的支持不完善而导致连接失败。Java 运行时环境（JRE）默认使用的是 IPv6 协议栈，但并不是所有的数据库服务器和网络配置都完全支持 IPv6。某些数据库（特别是在某些企业环境中）只支持 IPv4 或者对 IPv6 的支持不稳定。因此，JVM 在尝试连接数据库时，可能会因为 IPv6 的支持不完善而导致连接失败。Java 运行时环境（JRE）默认使用的是 IPv6 协议栈，但并不是所有的数据库服务器和网络配置都完全支持 IPv6。某些数据库（特别是在某些企业环境中）只支持 IPv4 或者对 IPv6 的支持不稳定。因此，JVM 在尝试连接数据库时，可能会因为 IPv6 的支持不完善而导致连接失败。','[\"160\",\"200\",\"250\",\"10000\"]','测试','C',5),(46,31,'多选题','褚名媛的真实身高可以是多少','[\"160\",\"200\",\"250\",\"10000\"]','可以是多少，可以不遵循实际情况','A,B,C,D',5),(47,31,'简答题','袁玉说傻波依不，说出理由？','[]','我说是就是','是，不讲理',5),(50,31,'单选题','int类型是 32 位（bit），占 4 个字节（byte），int 是有符号整数类型，其取值范围是从\n -2^31 到 2^31-1 。例如，在一个简单的计数器程序中，如果使用int类型来存储计数值，\n它可以表示的最大正数是 2,147,483,647。如果计数值超过这个范围，就会发生溢出，导致\n结果不符合预期。\nlong类型是 64 位，占 8 个字节，long类型也是有符号整数类型，它的取值范围是从 -2^6\n3 到 2^63 -1 ，在处理较大的整数数值时，果int类型的取值范围不够，就需要使用long类\n型。例如，在一个文件传输程序中，文件的大小可能会很大，使用int类型可能无法准确表示\n，而long类型就可以很好地处理这种情况int类型是 32 位（bit），占 4 个字节（byte），\nint 是有符号整数类型，其取值范围是从 -2^31 到 2^31-1 。例如，在一个简单的计数器程\n序中，如果使用int类型来存储计数值，它可以表示的最大正数是 2,147,483,647。如果计数\n值超过这个范围，就会发生溢出，导致结果不符合预期。\nlong类型是 64 位，占 8 个字节，long类型','[\"可以\",\"不可以\",\"是的\",\"不是\"]','int类型是 32 位（bit），占 4 个字节（byte），int 是有符号整数类型，其取值范围是从 -2^31 到 2^31-1 。例如，在一个简单的计数器程序中，如果使用int类型来存储计数值，它可以表示的最大正数是 2,147,483,647。如果计数值超过这个范围，就会发生溢出，导致结果不符合预期。\nlong类型是 64 位，占 8 个字节，long类型也是有符号整数类型，它的取值范围是从 -2^63 到 2^63 -1 ，在处理较大的整数数值时，果int类型的取值范围不够，就需要使用long类型。例如，在一个文件传输程序中，文件的大小可能会很大，使用int类型可能无法准确表示，而long类型就可以很好地处理这种情况','C',5);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `realname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `gender` char(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `identity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'褚鸣源','z','z','12345678911','1234567810@qq.com','女','山东',10),(2,'刷子','粉色刷子','123456789asd','15975312345','4121024051@qq.com','男','未填写',2),(10,'未填写','chumingyuan','1','14563214512','1234567810@qq.com','空','未填写',1),(11,'aasdasdasd','a','a','14563214112','1234567891@qq.com','男','asdasd1',1),(12,'未填写','xiaozhai','12345678','14563214512','rcjkxfyhkbkwrivb@frvnukewrf.com','空','未填写',1),(13,'褚名媛','没名字','asdasdasd','61516112133','3182140172@qq.com','女','未填写',2),(19,'小褚','西瓜皮','asdasdasd','55842154515','3382140172@qq.com','空','未填写',1),(20,'小智','皮卡丘','asdasdasd','64198491155','1982540172@qq.com','男','未填写',1),(21,'未填写','没名字3','asdasdasd','15975254365','3395610172@qq.com','女','未填写',1),(22,'好好好','丹东百灵鸟','asdasdasd','15945161561','9656526800@qq.com','男','未填写',1),(23,'未填写','没名字5','asdasdasd','15000120879','1597531231@qq.com','空','未填写',1),(24,'小翟','小狄','asdasdasd','13574564555','16524017201@qq.com','男','未填写',10),(25,'袁玉','废物舔狗','asdasdasd','65665268000','4416416051@qq.com','女','未填写',1),(26,'未填写','阳伞将兵','asdasdasd','00000000000','5700562595@qq.com','男','未填写',10),(27,'海涛','霸气在此','asdasdasd','11111111111','4959859459@qq.com','空','未填写',1),(28,'小王','妹纸','asdasdasd','22222222222','1652401701@qq.com','男','未填写',1),(29,'小马','荆棘','asdasdasd','12345679878','4121024051@qq.com','男','未填写',1),(30,'小张','什么','asdasdasd','71515446545','2685595555@qq.com','空','未填写',1),(31,'t','t','t','71515446545','2685595555@qq.com','女','未填写',2),(32,'好','褚名媛','123456789asd','15975312345','1234567891@qq.com','女','未填写',2),(33,'未填写','农妇','tsc205211','19550973670','123456789@qq.com','空','未填写',10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notice_status`
--

DROP TABLE IF EXISTS `user_notice_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notice_status` (
  `ns_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_id` bigint NOT NULL COMMENT '公告ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读 0=未读 1=已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`ns_id`),
  KEY `fk_notice` (`notice_id`),
  KEY `idx_user_notice` (`user_id`,`notice_id`),
  CONSTRAINT `fk_notice` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`n_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户公告状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notice_status`
--

LOCK TABLES `user_notice_status` WRITE;
/*!40000 ALTER TABLE `user_notice_status` DISABLE KEYS */;
INSERT INTO `user_notice_status` VALUES (1,1,31,0,NULL);
/*!40000 ALTER TABLE `user_notice_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-27 16:08:06
