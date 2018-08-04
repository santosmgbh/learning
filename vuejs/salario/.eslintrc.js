module.exports = {
  root: true,
  parserOptions: {
    sourceType: 'module',
    parser: 'babel-eslint',
  },
  env: {
    browser: true,
  },
  extends: ['plugin:prettier/recommended', 'plugin:vue/recommended'],
  plugins: ['prettier', 'vue', 'html'],
  rules: {
    'prettier/prettier': 'error',

    'vue/html-indent': [
      'error',
      2,
      {
        attribute: 1,
        closeBracket: 0,
        alignAttributesVertically: true,
        ignores: [],
      },
    ],
  },
};
