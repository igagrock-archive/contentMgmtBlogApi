package com.wemater.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.hibernate.HibernateException;

import com.wemater.dao.UserDao;
import com.wemater.dto.User;
import com.wemater.service.PublicService;
import com.wemater.util.HibernateUtil;
import com.wemater.util.SessionUtil;
import com.wemater.util.Util;

public class StartExecutorforArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {

		SessionUtil su = new SessionUtil(HibernateUtil.getSessionFactory()
				.openSession());

		System.out.println("Inserting anonymous ");

		saveUpdateAnyonymous(su);

		System.out.println("Inserting anonymous--DONE ");

		System.out.println("Executor servlet started ");
		PublicService task = new PublicService();
		Util.StartExecutorService(task);
		System.out.println("started the executor service");

	}

	public void saveUpdateAnyonymous(SessionUtil su) {
		UserDao ud = new UserDao(su);
		User user = ud.createUser("Anonymous", "Anonymous", "as@gt.com",
				"btrstwidsdsd", "My name is Anonymous. I represent all the orphan articles");

		try {
			su.beginSessionWithTransaction();
			su.getSession().saveOrUpdate(user);
			su.CommitCurrentTransaction();

		} catch (HibernateException e) {
			su.rollBackCurrentTransaction();
			System.out
					.println("Anyonymous already inserted. NO NEED TO INSERT");
		}

	}

}