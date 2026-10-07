# TopNoticias

Em construção

Aplicativo todo feito com Jetpack Compose, vai utilizar Retrofit para acesso a API de notícias e utilizará arquitetura MVVM.

## Configuração

A chave da [NewsAPI](https://newsapi.org) não fica no código. Adicione-a ao `local.properties` (que não é versionado):

```properties
NEWS_API_KEY=sua_chave_aqui
```

Ou defina a variável de ambiente `NEWS_API_KEY` antes do build.
