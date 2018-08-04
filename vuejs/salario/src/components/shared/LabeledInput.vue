<template>
  <!--
    O componente labeled-input agrupa
    um label e um input
  -->
  <label>
    <span>{{ label }}</span>
    <input
      :id="customId"
      :disabled="disabled"
      :type="!!currency ? 'text' : 'number'"
      :value="formatValue"
      @input="!!onInputChange && onInputChange($event)"
      min="0"
    >
  </label>
</template>

<script>
export default {
  /**
   * Como o componente será
   * representado (tag)
   */
  name: 'labeled-input',

  /**
   * Propriedades (atributos)
   * do componente.
   *
   * É interessante definirmos
   * os tipos para facilitar
   * validações
   */
  props: {
    currency: {
      type: Boolean,
      default: false,
    },

    disabled: {
      type: Boolean,
      default: false,
    },

    customId: String,
    label: String,
    value: String,
    onInputChange: Function,
  },

  /**
   * Alguns métodos podem ser encapsulados
   * como "computed properties"
   */
  computed: {
    /**
     * Formatando o valor do input
     * caso o usuário tenha definido
     * a prop "currency"
     */
    formatValue: {
      get: function() {
        if (!this.currency) return this.value;

        return Number(this.value).toLocaleString('pt-BR', {
          style: 'currency',
          currency: 'BRL',
        });
      },
    },
  },
};
</script>

// Para estilos focados apenas
// no componente, utilizamos 'scoped'
<style scoped>
input,
span {
  display: block;
  width: 100%;
  font-size: 1.2em;
}

input {
  margin-bottom: 10px;
}
</style>
