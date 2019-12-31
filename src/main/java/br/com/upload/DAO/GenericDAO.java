package br.com.upload.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.upload.model.Aluno;
import br.com.upload.util.HibernateUtil;

public class GenericDAO<T> {

	private EntityManager entityManager = HibernateUtil.geEntityManager();

	public void salvar(T entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	public Aluno merge(Aluno aluno) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Aluno returnEntity = entityManager.merge(aluno);
		entityTransaction.commit();
		return returnEntity;
	}

}
