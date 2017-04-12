-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: generic_backend
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `configs`
--

DROP TABLE IF EXISTS `configs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configs` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `backup_dir` varchar(255) DEFAULT NULL,
  `backup_prefix` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `dump_command` varchar(255) DEFAULT NULL,
  `last_audit_date` datetime DEFAULT NULL,
  `restore_command` varchar(255) DEFAULT NULL,
  `update_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configs`
--

LOCK TABLES `configs` WRITE;
/*!40000 ALTER TABLE `configs` DISABLE KEYS */;
/*!40000 ALTER TABLE `configs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `model_type` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `roleable_type` varchar(255) DEFAULT NULL,
  `update_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permissions`
--

DROP TABLE IF EXISTS `user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_permissions` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `permission_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq4qlrabt4s0etm9tfkoqfuib1` (`permission_id`),
  KEY `FKkowxl8b2bngrxd1gafh13005u` (`user_id`),
  CONSTRAINT `FKkowxl8b2bngrxd1gafh13005u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKq4qlrabt4s0etm9tfkoqfuib1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permissions`
--

LOCK TABLES `user_permissions` WRITE;
/*!40000 ALTER TABLE `user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile_rules`
--

DROP TABLE IF EXISTS `user_profile_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile_rules` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `allowed` bit(1) NOT NULL,
  `model_type` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `user_profile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb7cub8fp4wwv6uxo3wspv1lh2` (`permission`),
  KEY `FKia2h8cde8e84iljqbgo9tx3q2` (`user_profile`),
  CONSTRAINT `FKb7cub8fp4wwv6uxo3wspv1lh2` FOREIGN KEY (`permission`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKia2h8cde8e84iljqbgo9tx3q2` FOREIGN KEY (`user_profile`) REFERENCES `user_profiles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile_rules`
--

LOCK TABLES `user_profile_rules` WRITE;
/*!40000 ALTER TABLE `user_profile_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_profile_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profiles`
--

DROP TABLE IF EXISTS `user_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profiles` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `update_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcehojvi0igsrkafrwo1aoigfi` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profiles`
--

LOCK TABLES `user_profiles` WRITE;
/*!40000 ALTER TABLE `user_profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by_id` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_id` varchar(255) DEFAULT NULL,
  `entity_status` varchar(255) DEFAULT NULL,
  `locked_until` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_id` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `approved` bit(1) NOT NULL,
  `confirmation_sent_at` datetime DEFAULT NULL,
  `confirmed` bit(1) NOT NULL,
  `crypted_password` varchar(128) NOT NULL,
  `current_login_at` datetime DEFAULT NULL,
  `current_login_ip` varchar(255) DEFAULT NULL,
  `email` varchar(250) NOT NULL,
  `failed_login_count` int(11) DEFAULT NULL,
  `first_name` varchar(400) NOT NULL,
  `last_logged_in_at` datetime DEFAULT NULL,
  `last_login_at` datetime DEFAULT NULL,
  `last_login_ip` varchar(255) DEFAULT NULL,
  `last_name` varchar(400) NOT NULL,
  `login_count` int(11) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password_reset_last_state_change_at` datetime DEFAULT NULL,
  `password_reset_status` varchar(255) DEFAULT NULL,
  `persistence_token` varchar(255) DEFAULT NULL,
  `personal_email` varchar(400) DEFAULT NULL,
  `prefix` varchar(400) DEFAULT NULL,
  `roles_text` longtext,
  `salutation` varchar(400) DEFAULT NULL,
  `single_access_token` datetime DEFAULT NULL,
  `time_zone` varchar(40) DEFAULT NULL,
  `user_category` varchar(255) NOT NULL,
  `user_role` varchar(255) DEFAULT NULL,
  `user_profile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKfnwld7rqcs3fyvnme81wte76f` (`user_role`),
  KEY `FKcgs2bn6v33a5f3qvtvmg1njd2` (`user_profile`),
  CONSTRAINT `FKcgs2bn6v33a5f3qvtvmg1njd2` FOREIGN KEY (`user_profile`) REFERENCES `user_profiles` (`id`),
  CONSTRAINT `FKfnwld7rqcs3fyvnme81wte76f` FOREIGN KEY (`user_role`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('b137363f-5211-46c6-9eeb-38c6b60212b1',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'2017-04-12 16:56:31',NULL,2,NULL,'','',NULL,'\0','$2a$10$9Tu06bLuYb8dbcd86mhyau7qvIjvdLdVOK9sL/wl1c1VsJmraCJJG','2017-04-12 16:56:31','127.0.0.1 (127.0.0.1:55258)','kenedy.odero@gmail.com',NULL,'Odero',NULL,'2017-04-12 16:56:31',NULL,'Kennedy',1,NULL,NULL,NULL,'bq9aa6t6al3dbd77iktotv7eej',NULL,NULL,NULL,NULL,NULL,NULL,'ADMIN',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-12 16:58:36
