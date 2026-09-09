import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Processador {
    
    public static void main(String[] args) {
        
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_poliglota", "root", "");
        ) {
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM alunos WHERE matricula = 'Pendente' LIMIT 1");
            
            if (rs.next()) {
                int id = rs.getInt("id");
                
                String nome = rs.getString("nome").toUpperCase();
                
                String matriculaFicticia = "MAT-" + (1000 + id);
                
                stmt.executeUpdate("UPDATE alunos SET nome = '" + nome + "', matricula = '" + matriculaFicticia + "' WHERE id = " + id);
                
                System.out.println("Java processou o aluno: " +
