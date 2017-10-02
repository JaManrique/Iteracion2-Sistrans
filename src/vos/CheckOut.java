package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CheckOut {
		@JsonProperty(value="id")
		private Integer id;
		
		@JsonProperty(value="entregado")
		private Integer entregado;
		
		@JsonProperty(value="tiempor")
		private String tiempor;
		
		public CheckOut(@JsonProperty(value="id")Integer pNom,@JsonProperty(value="entregado")Integer ctrn,
				@JsonProperty(value="tiempor")String a) {
			super();
			id=pNom;
			entregado=ctrn;
			tiempor=a;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getEntregado() {
			return entregado;
		}

		public void setEntregado(Integer entregado) {
			this.entregado = entregado;
		}

		public String getTiempor() {
			return tiempor;
		}

		public void setTiempor(String tiempor) {
			this.tiempor = tiempor;
		}
		
}
