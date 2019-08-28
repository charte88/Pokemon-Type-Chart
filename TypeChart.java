package TypeChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TypeChart {
    public static void main(String[] args) {
        Scanner intScan = new Scanner(System.in);

        while (true) {
            System.out.print("How many types does the defending Pokemon have (1 or 2, input 0 to exit): ");
            while (!intScan.hasNext("[012]")) {
                System.out.print("Not a valid number, please try again: ");
                intScan.next();
            }
            int totalTypes = intScan.nextInt();

            if (totalTypes == 0) {
                System.out.println("\n-------THANK YOU-------");
                break;
            }
            System.out.println();

            typeEffectiveness(totalTypes);
        }
    }

    public static void typeEffectiveness(int totalTypes) {
        Scanner scan = new Scanner(System.in);
        String defendType1Search = null;
        String defendType2Search = null;

        if (totalTypes == 1) {
            System.out.print("Please enter in the type of the defending Pokemon: ");
        } else {
            System.out.print("Please enter in the types of the defending Pokemon: ");
        }

        String defendingTypes = scan.nextLine();
        String[] defendPokemonType = defendingTypes.split(" ");

        System.out.print("Please enter in your Pokemon's attacking type move: ");
        String attackSearch = scan.nextLine();
        System.out.println();

        if (defendPokemonType.length == 1) {
            defendType1Search = defendPokemonType[0];

            List<Database> defendValues = new ArrayList<>();
            List<Database> attackValues= new ArrayList<>();
            defendValues = getDefendingType(defendType1Search);
            attackValues = getAttackerType(attackSearch);

            defendValues.retainAll(attackValues);

            if (defendValues.size() == 0) {
                System.out.println("No results found, please check your spelling and try again!");
            }

            defendValues.forEach(System.out::println);
            System.out.println();

        } else if (defendPokemonType.length == 2) {
            defendType1Search = defendPokemonType[0];
            defendType2Search = defendPokemonType[1];

            List<Database> defendValues = new ArrayList<>();
            List<Database> defendValues2 = new ArrayList<>();
            List<Database> attackValues = new ArrayList<>();

            defendValues = getDefendingType(defendType1Search);
            defendValues2 = getDefendingType(defendType2Search);
            attackValues = getAttackerType(attackSearch);

            defendValues.retainAll(attackValues);
            defendValues2.retainAll(attackValues);

            if (defendValues.size() == 0 || defendValues2.size() == 0) {
                System.out.println("No results found, please check your spelling and try again!");
            } else {
                String damageEffectiveness = compareTypeEffectiveness(defendValues, defendValues2);
                System.out.println("A " + attackSearch + " type attack " + damageEffectiveness.toLowerCase() + " against a "
                        + defendType1Search + "/" + defendType2Search + " type Pokemon");
            }
            System.out.println();
        }
    }

    public static List<Database> getAttackerType(String name) {
        List<Database> response = new ArrayList<>();
        for (Database inquiry : Database.values()) {
            if (inquiry.getAttacker().equalsIgnoreCase(name)) {
                response.add(inquiry);
            }
        }
        return response;
    }

    public static List<Database> getDefendingType(String name) {
        List<Database> response = new ArrayList<>();
        for (Database inquiry : Database.values()) {
            if (inquiry.getDefender().equalsIgnoreCase(name)) {
                response.add(inquiry);
            }
        }
        return response;
    }

    public static String compareTypeEffectiveness(List<Database> defendValues, List<Database> defendValues2) {
        if (defendValues.get(0).checkEquals(defendValues2.get(0))) {
            if (defendValues.get(0).getDamageEffectiveness().equals("Super-effective"))  {
                return "is 4x Super-effective";
            } else if (defendValues.get(0).getDamageEffectiveness().equals("Not very effective")) {
                return "is 1/4x not very effective";
            } else {
                return "is Normal";
            }
        } else {
            if ((defendValues.get(0).getDamageEffectiveness().equals("No effect")) || (defendValues2.get(0).getDamageEffectiveness().equals("No effect"))) {
                return "has No effect";
            } else if ((defendValues.get(0).getDamageEffectiveness().equals("Super-effective")) && (defendValues2.get(0).getDamageEffectiveness().equals("Not very effective"))) {
                return "is Normal";
            } else if ((defendValues.get(0).getDamageEffectiveness().equals("Not very effective")) && (defendValues2.get(0).getDamageEffectiveness().equals("Super-effective"))) {
                return "is Normal";
            } else if ((defendValues.get(0).getDamageEffectiveness().equals("Super-effective")) && (defendValues2.get(0).getDamageEffectiveness().equals("Normal"))) {
                return "is Super-effective";
            } else if ((defendValues.get(0).getDamageEffectiveness().equals("Normal")) && (defendValues2.get(0).getDamageEffectiveness().equals("Super-effective"))) {
                return "is Super-effective";
            } else {
                return "is Not very effective";
            }
        }
    }
}