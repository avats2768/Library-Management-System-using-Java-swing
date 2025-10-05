package MyLibrary;

public class Main {
    public static void main(String[] args) {
        System.out.println("🟢 Starting Main...");
        try {
            // Comment out Swing
            // UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
            System.out.println("👀 Trying to create Login object...");
            Login login = new Login();
            System.out.println("✅ Login created.");
        } catch (Exception e) {
            System.err.println("🔥 ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
