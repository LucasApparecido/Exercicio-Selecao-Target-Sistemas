public class Ex4_Faturamento_Estados {

    public static void main(String[] args) {
        double sp = 67836.43;
        double rj = 36678.66;
        double mg = 29229.88;
        double es = 27165.48;
        double others = 19849.53;

        double totalRevenue = sp + rj + mg + es + others;

        System.out.println("Percentual de representação de cada estado no faturamento total:");

        System.out.printf("SP: %.2f%%\n", (sp / totalRevenue) * 100);
        System.out.printf("RJ: %.2f%%\n", (rj / totalRevenue) * 100);
        System.out.printf("MG: %.2f%%\n", (mg / totalRevenue) * 100);
        System.out.printf("ES: %.2f%%\n", (es / totalRevenue) * 100);
        System.out.printf("Outros: %.2f%%\n", (others / totalRevenue) * 100);
    }
}
