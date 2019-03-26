package io.tofu.teprl.machines;

public abstract class Mecanismo {
	protected String nome;
	// protected ImagemMecanismo imagem;
	
	public Mecanismo(String nome) {
		this.nome = nome;
	}
	
	// TODO - queremos suportar mecanismos com mesmo nome, portanto isso tem que mudar
	@Override
	public int hashCode() {
		return nome.hashCode();
	}
	
	public String getNome() {
		return nome;
	}
	
	/* public ImagemMecanismo getImagem() {
		return imagem;
	} */
	
	// public abstract void atualizarImagem(Integer IndiceMecanismo);
}

