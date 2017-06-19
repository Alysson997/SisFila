package alysson.com.br.aplicativo.database;

/**
 * Created by Yago on 26/01/2016.
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

    public static String getCreateTipoAtendimento(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS TIPO_ATENDIMENTO( ");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("DESCRICAO VARCHAR (50), ");
        sqlBuilder.append("EMPRESA_ID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(EMPRESA_ID) REFERENCES EMPRESA(_id) ");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    public static String getCreateAtendente(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ATENDENTE( ");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME VARCHAR (50), ");
        sqlBuilder.append("EMAIL VARCHAR (50), ");
        sqlBuilder.append("SEXO VARCHAR (10), ");
        sqlBuilder.append("DATA_NASCIMENTO DATE, ");
        sqlBuilder.append("EMPRESA_ID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(EMPRESA_ID) REFERENCES EMPRESA(_id) ");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

}
