//package com.yh.daoTest;
//
//import java.io.IOException;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Test;
//
////import com.yh.dao.UserDao;
////import com.yh.entity.User;
//
//public class UserDaoTest {
//
//
//    @Test
//    public void findUserById() {
//        SqlSession sqlSession = getSessionFactory().openSession();
//      //  UserDao userMapper = sqlSession.getMapper(UserDao.class);
////        User user = userMapper.findUserById(1);
////    	System.out.println(user.getC1());
////        System.out.println(user.getId());
////        System.out.println(user.getC2());
////        System.out.println(user.getNum());
////        System.out.println(user);
//        
////        sqlSession.close();
//        
//        try {
//            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
//            // 第二个参数：指定传入sql的参数：这里是用户id
//            //User user1 = sqlSession.selectOne("MyMapper.selectUser", 1);
//        	//User user = userMapper.findUserById(1);
//        	System.out.println(user.getC1());
//            System.out.println(user.getId());
//            System.out.println(user.getC2());
//            System.out.println(user.getNum());
//            System.out.println(user);
//         } finally {
//            sqlSession.close();
//         }
//        
//        //Assert.assertNotNull("没找到数据", user);
//    }
//
//    //Mybatis 通过SqlSessionFactory获取SqlSession, 然后才能通过SqlSession与数据库进行交互
//    private static SqlSessionFactory getSessionFactory() {
//        SqlSessionFactory sessionFactory = null;
//        String resource = "configuration.xml";
//        try {
//            sessionFactory = new SqlSessionFactoryBuilder().build(Resources
//                    .getResourceAsReader(resource));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sessionFactory;
//    }
//}
