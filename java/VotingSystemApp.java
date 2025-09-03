import java.util.Scanner;

public class VotingSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter number of candidates: ");
            int numberOfCandidates = Integer.parseInt(scanner.nextLine());

            String[] candidateNames = new String[numberOfCandidates];
            int[] candidateVotes = new int[numberOfCandidates];

            for (int candidateIndex = 0; candidateIndex < numberOfCandidates; candidateIndex++) {
                String candidateName;
                boolean nameExists;
                do {
                    System.out.print("Enter name of candidate " + (candidateIndex + 1) + ": ");
                    candidateName = scanner.nextLine();
                    nameExists = false;
                    for (String existingName : candidateNames) {
                        if (candidateName.equalsIgnoreCase(existingName)) {
                            System.out.println("Name already exists. Try again.");
                            nameExists = true;
                            break;
                        }
                    }
                } while (nameExists);
                candidateNames[candidateIndex] = candidateName;
                candidateVotes[candidateIndex] = 0;
            }

            System.out.print("\nEnter number of voters: ");
            int numberOfVoters = Integer.parseInt(scanner.nextLine());

            String[] voterNames = new String[numberOfVoters];
            int[] voterAges = new int[numberOfVoters];
            String[] voterIDs = new String[numberOfVoters];
            boolean[] voterHasVoted = new boolean[numberOfVoters];

            for (int voterIndex = 0; voterIndex < numberOfVoters; voterIndex++) {
                System.out.print("Enter name of voter " + (voterIndex + 1) + ": ");
                String voterName = scanner.nextLine();

                int voterAge;
                do {
                    System.out.print("Enter age of voter " + (voterIndex + 1) + ": ");
                    voterAge = Integer.parseInt(scanner.nextLine());
                    if (voterAge < 18) System.out.println("Voter must be at least 18 years old.");
                } while (voterAge < 18);

                String voterID;
                boolean idExists;
                do {
                    System.out.print("Enter unique ID of voter " + (voterIndex + 1) + ": ");
                    voterID = scanner.nextLine();
                    idExists = false;
                    for (String existingID : voterIDs) {
                        if (voterID.equalsIgnoreCase(existingID)) {
                            System.out.println("ID already exists. Try again.");
                            idExists = true;
                            break;
                        }
                    }
                } while (idExists);

                voterNames[voterIndex] = voterName;
                voterAges[voterIndex] = voterAge;
                voterIDs[voterIndex] = voterID;
                voterHasVoted[voterIndex] = false;
            }

            System.out.println("\n==== Voting Process ====");
            for (int voterIndex = 0; voterIndex < numberOfVoters; voterIndex++) {
                if (!voterHasVoted[voterIndex]) {
                    System.out.println("\nHello, " + voterNames[voterIndex] + " (Age: " + voterAges[voterIndex] + ", ID: " + voterIDs[voterIndex] + ")");
                    System.out.println("Please cast your vote:");
                    for (int candidateIndex = 0; candidateIndex < numberOfCandidates; candidateIndex++) {
                        System.out.println((candidateIndex + 1) + ". " + candidateNames[candidateIndex]);
                    }

                    int chosenCandidate;
                    do {
                        System.out.print("Enter candidate number: ");
                        chosenCandidate = Integer.parseInt(scanner.nextLine());
                    } while (chosenCandidate < 1 || chosenCandidate > numberOfCandidates);

                    candidateVotes[chosenCandidate - 1]++;
                    voterHasVoted[voterIndex] = true;
                    System.out.println("Vote cast successfully!");
                }
            }

            for (int firstCandidate = 0; firstCandidate < numberOfCandidates - 1; firstCandidate++) {
                for (int secondCandidate = firstCandidate + 1; secondCandidate < numberOfCandidates; secondCandidate++) {
                    if (candidateVotes[secondCandidate] > candidateVotes[firstCandidate]) {
                        int tempVotes = candidateVotes[firstCandidate];
                        candidateVotes[firstCandidate] = candidateVotes[secondCandidate];
                        candidateVotes[secondCandidate] = tempVotes;

                        String tempName = candidateNames[firstCandidate];
                        candidateNames[firstCandidate] = candidateNames[secondCandidate];
                        candidateNames[secondCandidate] = tempName;
                    }
                }
            }

            System.out.println("\n==== Election Results ====");
            int highestVotes = candidateVotes[0];
            boolean tie = false;
            for (int candidateIndex = 0; candidateIndex < numberOfCandidates; candidateIndex++) {
                System.out.println(candidateNames[candidateIndex] + " got " + candidateVotes[candidateIndex] + " votes.");
                if (candidateIndex > 0 && candidateVotes[candidateIndex] == highestVotes) tie = true;
            }

            if (tie) {
                System.out.println("\nIt's a tie! No clear winner.");
            } else {
                System.out.println("\nWinner: " + candidateNames[0]);
            }

            System.out.print("\nDo you want to run another election? (yes/no): ");
            String continueElection = scanner.nextLine();
            if (!continueElection.equalsIgnoreCase("yes")) break;
        }
    }
}
