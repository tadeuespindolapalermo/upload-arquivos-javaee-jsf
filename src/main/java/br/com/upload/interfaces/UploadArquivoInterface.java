package br.com.upload.interfaces;

import br.com.upload.model.Aluno;
import br.com.upload.model.Entidade;

public interface UploadArquivoInterface {
	
	void salvarArquivo(Entidade entidade);
	
	Aluno mergeAluno(Aluno aluno);

}
