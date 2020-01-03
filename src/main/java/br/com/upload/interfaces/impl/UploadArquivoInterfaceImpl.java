package br.com.upload.interfaces.impl;

import java.util.List;

import br.com.upload.DAO.GenericDAO;
import br.com.upload.interfaces.UploadArquivoInterface;
import br.com.upload.model.Aluno;
import br.com.upload.model.Entidade;

public class UploadArquivoInterfaceImpl extends GenericDAO<Entidade> implements UploadArquivoInterface {

	@Override
	public void salvarArquivo(Entidade entidade) {
		salvar(entidade);
	}

	@Override
	public Aluno mergeAluno(Aluno aluno) {
		return merge(aluno);
	}

	@Override
	public List<Object> listarArquivo() {
		return listar();
	}

	@Override
	public Object buscarArquivo(String fileDownloadId) {
		return buscar(fileDownloadId);
	}

}
