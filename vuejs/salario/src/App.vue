<template>
  <div id="app">
    <div class="row">
      <img
        src="./assets/logo.png"
        width="50px"
      >
        <h3>
          Cálculo de salário com Vue.js
        </h3>
    </div>
    <br>
    <br>

    <div class="content">
      <div class="mainContent">
        <h3>Cálculo em tempo real</h3>

        <!--
          <labeled-input> é um componente customizado

          No primeiro input, é enviado o método updateSalario com
          o evento customizado onInputChange. A notação de ':'
          substitui a notação v-bind: (que é mais verbosa).
          v-bind: faz com que o conteúdo deixe de ser string e passe
          a ser JavaScript. Neste caso, o método updateSalario()

          customId é utilizado para enviar um id ao input. O id
          será necessário para obter a referênia ao componente
          durante os cálculos
        -->
        <labeled-input
          :onInputChange="updateSalario"
          customId="inputSalarioBruto"
          label="Salário bruto:"
        />

        <!--
          Alguns inputs foram desabilitados
          para evitar edição pelo usuário.
          Nesses casos, os dados serão
          calculados automaticamente através
          da classe Salario.

          "currency" indica uma formatação
          especial para esses inputs (moeda)

          :value é v-bind vinculando aos getters
          da classe Salario
        -->
        <labeled-input
          :value="salario.baseINSS"
          currency
          label="Base INSS:"
          disabled
        />

        <labeled-input
          :value="salario.descontoINSS"
          currency
          label="Desconto INSS:"
          disabled
        />

        <labeled-input
          :value="salario.baseIRPF"
          currency
          label="Base IRPF:"
          disabled
        />

        <labeled-input
          :value="salario.descontoIRPF"
          currency
          label="Desconto IRPF:"
          disabled
        />

        <labeled-input
          :value="salario.salarioLiquido"
          currency
          label="Salário líquido:"
          customId="inputSalarioLiquido"
          disabled
        />
      </div>

      <div class="sideContent">
        <h3>
          Cálculo reverso com Observables
        </h3>

        <labeled-input
          :value="salarioLiquidoDesejado"
          :onInputChange="updateSalarioLiquidoDesejado"
          customId="inputSalarioLiquidoDesejado"
          label="Salário líquido desejado:"
        />

        <!--
          No botão, há a notação de '@', que simplifica
          a notação v-on: (mais verbosa). Essa notação
          é utilizada para a definição de eventos
        -->
        <button @click="findSalarioBrutoFromLiquido">
          Calcular salário bruto correspondente
        </button>
      </div>
    </div>

  </div>
</template>

<script>
import { interval, timer, Observable } from 'rxjs';
import { takeUntil, map, filter } from 'rxjs/operators';

/**
 * Classe que calcula dados de salários
 */
import { Salario } from './Salario';

/**
 * Componente LabeledInput
 */
import LabeledInput from './components/shared/LabeledInput.vue';

export default {
  /**
   * Nome do componente
   */
  name: 'App',

  /**
   * Indicativo de quais outros componentes
   * são utilizados por 'App'. Aqui é possível
   * inclusive renomearmos a "tag" do
   * componente.
   */
  components: {
    'labeled-input': LabeledInput,
  },

  /**
   * Dados que serão monitorados (observable) e são,
   * portanto, reativos. A boa prática prega que seja
   * uma função com retorno, conforme código abaixo.
   */
  data() {
    return {
      /**
       * Objeto da classe salário
       */
      salario: new Salario(0),

      /**
       * Indicativo de salário líquido
       * desejado inicial
       */
      salarioLiquidoDesejado: '5000',
    };
  },

  /**
   * Métodos do componente
   */
  methods: {
    /**
     * Atualiza o objeto salário conforme valor
     * do input editável pelo usuário
     */
    updateSalario: function(event) {
      /**
       * Criamos um método mais "interno" porque a
       * lógica é utilizada em outros pontos no código
       */
      this._internalUpdateSalario(+event.target.value);
    },

    /**
     * Atualizamos o salário de forma imutável, ou seja,
     * instanciando um novo objeto da classe Salario, que
     * já faz todos os cálculos necessários no construtor.
     * Assim, ao instanciar o novo objeto, todos os inputs
     * que observam this.salario irão reagir
     */
    _internalUpdateSalario: function(newSalario) {
      this.salario = new Salario(+newSalario);
    },

    /**
     * Atualiza o salário líquido desejado
     */
    updateSalarioLiquidoDesejado: function(event) {
      this.salarioLiquidoDesejado = event.target.value;
    },

    /**
     * Método para encontrar um salário bruto necessário
     * para o salário líquido desejado
     */
    findSalarioBrutoFromLiquido: function() {
      /**
       * Obtendo o input que define o salário bruto
       * e atualizando-o com o salário líquido desejado,
       * evitando iterações desnecessárias.
       *
       * $el é uma variável especial do Vue que aponta
       * para o elemento monitorado pelo Vue. Na maioria
       * dos casos, é uma div com id #app (verifique index.html).
       * Com $el, conseguimos "navegar" dentro dos filhos do
       * elemento (children), obtendo referências com querySelector,
       * por exemplo.
       */
      const inputSalarioBruto = this.$el.querySelector('#inputSalarioBruto');
      inputSalarioBruto.value = this.salarioLiquidoDesejado;

      /**
       * Obtendo referência ao input de salário líquido atual
       */
      const inputSalarioLiquido = this.$el.querySelector('#inputSalarioLiquido');

      /**
       * Criando observable que, a cada 1 milisegundo, incrementa
       * o salário bruto e o recalcula no estado da aplicação. Por
       * fim, retorna o salarioLiquido obtido após o cálculo.
       *
       */
      const obs$ = interval(1).pipe(
        /**
         * Transformação de dados (map)
         */
        map(() => {
          /**
           * Obtendo o salário líquido do momento
           */
          const currentValue = +this.salario.salarioLiquido;

          /**
           * Calculando a diferença entre o salário líquido do momento
           * e o salário líquido desejado
           */
          const difference = Math.abs(currentValue - +this.salarioLiquidoDesejado);

          /**
           * Quando a diferença for menor que 5 reais, o
           * incremento passa a ser de 1 centavo (0.01)
           * para uma melhor precisão no cálculo sem que
           * o mesmo se torne lento, ou seja, enquanto a
           * diferença for maior que 5 reais o incremento
           * é de "1 em 1 real"
           */
          const increment = difference >= 5 ? 1 : 0.01;

          /**
           * Incrementando o valor no salário bruto
           * e formatando para 2 casas decimais
           */
          inputSalarioBruto.value = (+inputSalarioBruto.value + increment).toFixed(2);

          /**
           * Atualizando o salário bruto. Quando atualizamos o valor
           * "na mão", o Vue não consegue monitorar as
           * mudanças
           */
          this._internalUpdateSalario(inputSalarioBruto.value);

          /**
           * Por fim, retornamos o salário líquido atual
           */
          return this.salario.salarioLiquido;
        }),
      );

      /**
       * Observable para ser utilizado com takeUntil,
       * que será a condição de término da execução
       * (salarioLiquido do estado maior ou igual ao salarioLiquido desejado)
       */
      const match$ = obs$.pipe(
        filter(currentValue => +currentValue >= +this.salarioLiquidoDesejado),
      );

      /**
       * Acionamos, por fim, a execução do observable com
       * subscribe()
       */
      obs$.pipe(takeUntil(match$)).subscribe();
    },
  },
};
</script>

<style>
body {
  font-family: Arial, Helvetica, sans-serif;
}

.row {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
}

.content {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.mainContent {
  border: 1px solid gray;
  padding: 10px;
  margin-right: 30px;
  width: 50%;
}

.sideContent {
  border: 1px solid gray;
  padding: 10px;
  margin-right: 30px;
  width: 50%;
}
</style>
