USE [master]
GO

/****** Object:  Database [website-monitor]    Script Date: 1/6/2021 12:41:41 AM ******/
CREATE DATABASE [website-monitor]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'website-monitor', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\website-monitor.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'website-monitor_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\website-monitor_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [website-monitor].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [website-monitor] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [website-monitor] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [website-monitor] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [website-monitor] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [website-monitor] SET ARITHABORT OFF 
GO

ALTER DATABASE [website-monitor] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [website-monitor] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [website-monitor] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [website-monitor] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [website-monitor] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [website-monitor] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [website-monitor] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [website-monitor] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [website-monitor] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [website-monitor] SET  DISABLE_BROKER 
GO

ALTER DATABASE [website-monitor] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [website-monitor] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [website-monitor] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [website-monitor] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [website-monitor] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [website-monitor] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [website-monitor] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [website-monitor] SET RECOVERY FULL 
GO

ALTER DATABASE [website-monitor] SET  MULTI_USER 
GO

ALTER DATABASE [website-monitor] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [website-monitor] SET DB_CHAINING OFF 
GO

ALTER DATABASE [website-monitor] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [website-monitor] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

ALTER DATABASE [website-monitor] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [website-monitor] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO

ALTER DATABASE [website-monitor] SET QUERY_STORE = OFF
GO

ALTER DATABASE [website-monitor] SET  READ_WRITE 
GO

/*******Test Database*******/
USE [master]
GO

/****** Object:  Database [website-monitor-test]    Script Date: 1/6/2021 12:46:12 AM ******/
CREATE DATABASE [website-monitor-test]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'website-monitor-test', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\website-monitor-test.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'website-monitor-test_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\website-monitor-test_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [website-monitor-test].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [website-monitor-test] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [website-monitor-test] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [website-monitor-test] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [website-monitor-test] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [website-monitor-test] SET ARITHABORT OFF 
GO

ALTER DATABASE [website-monitor-test] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [website-monitor-test] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [website-monitor-test] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [website-monitor-test] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [website-monitor-test] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [website-monitor-test] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [website-monitor-test] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [website-monitor-test] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [website-monitor-test] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [website-monitor-test] SET  DISABLE_BROKER 
GO

ALTER DATABASE [website-monitor-test] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [website-monitor-test] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [website-monitor-test] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [website-monitor-test] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [website-monitor-test] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [website-monitor-test] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [website-monitor-test] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [website-monitor-test] SET RECOVERY FULL 
GO

ALTER DATABASE [website-monitor-test] SET  MULTI_USER 
GO

ALTER DATABASE [website-monitor-test] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [website-monitor-test] SET DB_CHAINING OFF 
GO

ALTER DATABASE [website-monitor-test] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [website-monitor-test] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

ALTER DATABASE [website-monitor-test] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [website-monitor-test] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO

ALTER DATABASE [website-monitor-test] SET QUERY_STORE = OFF
GO

ALTER DATABASE [website-monitor-test] SET  READ_WRITE 
GO

/*******Creating Tables********/

USE [website-monitor]
GO

/****** Object:  Table [dbo].[checkTbl]    Script Date: 12/25/2020 9:19:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[checkTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[website_url] [varchar](50) NOT NULL,
	[frequency] [int] NOT NULL,
	[enabled] [tinyint] NULL,
 CONSTRAINT [PK_checkTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[websiteMonitorTbl]    Script Date: 12/25/2020 9:21:07 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[websiteMonitorTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[website_url] [varchar](50) NOT NULL,
	[checkId] [int] NULL,
	[status] [varchar](50) NULL,
	[uptime] [smalldatetime] NULL,
	[downtime] [smalldatetime] NULL,
	[responseTime] [float] NULL,
 CONSTRAINT [PK_websiteMonitorTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


USE [website-monitor-test]
GO

/****** Object:  Table [dbo].[checkTbl]    Script Date: 12/25/2020 9:21:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[checkTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[website_url] [varchar](50) NOT NULL,
	[frequency] [int] NOT NULL,
	[enabled] [tinyint] NULL,
 CONSTRAINT [PK_checkTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[websiteMonitorTbl]    Script Date: 12/25/2020 9:22:11 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[websiteMonitorTbl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[website_url] [varchar](50) NOT NULL,
	[checkId] [int] NULL,
	[status] [varchar](50) NULL,
	[uptime] [smalldatetime] NULL,
	[downtime] [smalldatetime] NULL,
	[responseTime] [float] NULL,
 CONSTRAINT [PK_websiteMonitorTbl] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO