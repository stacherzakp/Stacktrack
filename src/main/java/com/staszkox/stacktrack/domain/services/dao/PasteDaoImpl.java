package com.staszkox.stacktrack.domain.services.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import com.staszkox.stacktrack.domain.common.TextUtils;
import com.staszkox.stacktrack.domain.tos.PasteHistoryDTO;
import com.staszkox.stacktrack.domain.tos.PasteSimpleDTO;
import com.staszkox.stacktrack.model.Paste;
import com.staszkox.stacktrack.model.enums.Privacy;

@Repository
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PasteDaoImpl implements PasteDao
{
	private static final String WILDCARD = "%";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean savePaste(Paste pasteContent)
	{
		sessionFactory.getCurrentSession().saveOrUpdate(pasteContent);
		return true;
	}

	@Override
	public PasteSimpleDTO getPaste(String uuid)
	{
		String queryStr = "SELECT p.title as title, p.content as content, p.author as author, p.createDate as createDate, " + 
						  "p.password as password " +
						  "FROM Paste p WHERE p.uuid LIKE :uuid";
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setResultTransformer(Transformers.aliasToBean(PasteSimpleDTO.class));
		query.setParameter("uuid", uuid);
		
		PasteSimpleDTO result = (PasteSimpleDTO) query.uniqueResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PasteHistoryDTO> getLatestPastes(Integer size)
	{
		String queryStr = "SELECT p.uuid as uuid, p.title as title, p.author as author, " + 
						  "p.createDate as createDate, p.shortContent as shortContent " + 
						  "FROM Paste p WHERE p.privacy = :privacy ORDER BY p.createDate DESC";
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setResultTransformer(Transformers.aliasToBean(PasteHistoryDTO.class));
		query.setParameter("privacy", Privacy.PUBLIC);
		query.setMaxResults(size);
		
		List<PasteHistoryDTO> result = (List<PasteHistoryDTO>) query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PasteHistoryDTO> searchPastes(String title, String author)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT p.uuid as uuid, p.title as title, p.author as author, " + 
					   "p.createDate as createDate, p.shortContent as shortContent " + 
					   "FROM Paste p WHERE p.privacy = :privacy ");
		builder.append(TextUtils.isNotEmpty(title) ? "AND p.title LIKE :title " : "");
		builder.append(TextUtils.isNotEmpty(author) ? "AND p.author LIKE :author " : "");
		builder.append("ORDER BY p.createDate DESC");

		
		Query query = sessionFactory.getCurrentSession().createQuery(builder.toString());
		query.setResultTransformer(Transformers.aliasToBean(PasteHistoryDTO.class));
		query.setParameter("privacy", Privacy.PUBLIC);
		
		if (TextUtils.isNotEmpty(title))
		{
			query.setParameter("title", WILDCARD + title + WILDCARD);
		}
		
		if (TextUtils.isNotEmpty(author))
		{
			query.setParameter("author", WILDCARD + author + WILDCARD);
		}
		
		List<PasteHistoryDTO> result = (List<PasteHistoryDTO>) query.list();
		return result;
	}
}
