package aula2603.jdbc;

import java.sql.Connection;

public class MinhaConexao {

    //método da classe (static)
    public static Connection conexao(){
        ConexaoJDBC conexao = new ConexaoH2();
        return conexao.criarConexao();
    }

}
