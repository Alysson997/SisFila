package alysson.com.br.aplicativo.database;

/**
 * Created by Alysson on 26/01/2016.
 *
 * Essa classe contém metodos que retornam uma String
 * com o código SQL
 */
public class ScriptSQL{

    public static String getCreateEmpresa(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS EMPRESA( ");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("RAZAO_SOCIAL VARCHAR (50), ");
        sqlBuilder.append("FANTASIA VARCHAR (50), ");
        sqlBuilder.append("CNPJ VARCHAR (20), ");
        sqlBuilder.append("EMAIL VARCHAR (50), ");
        sqlBuilder.append("TELEFONE VARCHAR (15), ");
        sqlBuilder.append("CEP VARCHAR (15), ");
        sqlBuilder.append("LOGRADOURO VARCHAR (50), ");
        sqlBuilder.append("SENHA VARCHAR (30) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateuUsuario(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS USUARIO( ");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME VARCHAR (50), ");
        sqlBuilder.append("EMAIL VARCHAR (50), ");
        sqlBuilder.append("SENHA VARCHAR (30), ");
        sqlBuilder.append("EMPRESA_ID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(EMPRESA_ID) REFERENCES EMPRESA(_id) ");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    /*
    public static String getCreateCidade(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CIDADE( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME VARCHAR (50), ");
        sqlBuilder.append("ESTADO_ID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(ESTADO_ID) REFERENCES ESTADO(_id) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateCliente(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CLIENTE( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME_FANTASIA VARCHAR (50), ");
        sqlBuilder.append("RAZAO_SOCIAL VARCHAR (50), ");
        sqlBuilder.append("CNPJ VARCHAR (20), ");
        sqlBuilder.append("NOME VARCHAR (50), ");
        sqlBuilder.append("ENDERECO VARCHAR (100), ");
        sqlBuilder.append("NUMERO VARCHAR (6), ");
        sqlBuilder.append("BAIRRO VARCHAR (50), ");
        sqlBuilder.append("CEP VARCHAR (15), ");
        sqlBuilder.append("TELEFONE VARCHAR(14), ");
        sqlBuilder.append("EMAIL VARCHAR (40), ");
        sqlBuilder.append("DATA_CADASTRO DATE, ");
        sqlBuilder.append("OBSERVACAO VARCHAR (10), ");
        sqlBuilder.append("STATUS VARCHAR (12), ");
        sqlBuilder.append("CIDADE_ID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(CIDADE_ID) REFERENCES CIDADE(_id) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateProduto(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PRODUTO( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("DESCRICAO VARCHAR (50), ");
        sqlBuilder.append("VALOR_ADESAO REAL, ");
        sqlBuilder.append("VALOR_MENSALIDADE REAL ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    */


}
