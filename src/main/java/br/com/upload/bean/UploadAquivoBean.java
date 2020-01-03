package br.com.upload.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.upload.interfaces.UploadArquivoInterface;
import br.com.upload.interfaces.impl.UploadArquivoInterfaceImpl;
import br.com.upload.model.Aluno;
import br.com.upload.model.Entidade;

@RequestScoped
@ManagedBean(name = "uploadAquivoBean")
public class UploadAquivoBean {

	private Entidade entidade = new Entidade();

	private UploadArquivoInterface uploadArquivoInterface = new UploadArquivoInterfaceImpl();

	private Part arquivo;
	
	private List<Object> list = new ArrayList<>();
	
	public void upload() throws Exception {
		
		// CSV
		try (Scanner scanner = new Scanner(arquivo.getInputStream(), "UTF-8")) {
			
			scanner.useDelimiter(";");
			
			while (scanner.hasNext()) {
				
				String linha = scanner.nextLine();
				
				if (linha != null && !linha.trim().isEmpty()) {				
					linha = linha.replaceAll("\"", "");
					
					String[] dados = linha.split("\\;");
					System.out.println("Nome: " + dados[0] + " - E-mail " + dados[1]);
					
					Aluno aluno = new Aluno();
					aluno.setNome(dados[0]);
					aluno.setEmail(dados[1]);
					
					uploadArquivoInterface.mergeAluno(aluno);					
				}
			}
		}
		
		byte[] arquivoByte = toByteArray(arquivo.getInputStream());
		entidade.setArquivo(arquivoByte);
		uploadArquivoInterface.salvarArquivo(entidade);		
		
		carregarLista();
	}
	
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = params.get("fileDownloadId");
		
		Object object = uploadArquivoInterface.buscarArquivo(fileDownloadId);
		
		Entidade entidadeArquivo = (Entidade) object;
		System.out.println(entidadeArquivo.getDescricao());
		
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getResponse();
		
		response.addHeader("Content-Disposition", "attachment; filename=download.csv");
		response.setContentType("application/octet-stream");
		response.setContentLength(entidadeArquivo.getArquivo().length);
		response.getOutputStream().write(entidadeArquivo.getArquivo());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}

	@PostConstruct
	private void carregarLista() {
		list = uploadArquivoInterface.listarArquivo();
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public UploadArquivoInterface getUploadArquivoInterface() {
		return uploadArquivoInterface;
	}

	public void setUploadArquivoInterface(UploadArquivoInterface uploadArquivoInterface) {
		this.uploadArquivoInterface = uploadArquivoInterface;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
	
	public List<Object> getList() {
		return list;
	}
	
	/*Converte a entrada de fluxo de dados do arquivo para byte[]*/
	private byte[] toByteArray(InputStream is) throws Exception{
		
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 int reads = is.read();
	 while (reads != -1){
		 baos.write(reads);
		 reads = is.read();
	 }	 
	 return baos.toByteArray();	
	}

}
