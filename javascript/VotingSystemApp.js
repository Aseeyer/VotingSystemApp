const prompt = require("prompt-sync")({ sigint: true });

while (true) {
    let numberOfCandidates = parseInt(prompt("Enter number of candidates: "));

    let candidateNames = new Array(numberOfCandidates);
    let candidateVotes = new Array(numberOfCandidates).fill(0);

    for (let i = 0; i < numberOfCandidates; i++) {
        let candidateName;
        let nameExists;
        do {
            candidateName = prompt(`Enter name of candidate ${i + 1}: `);
            nameExists = candidateNames.some(name => name && name.toLowerCase() === candidateName.toLowerCase());
            if (nameExists) console.log("Name already exists. Try again.");
        } while (nameExists);
        candidateNames[i] = candidateName;
    }

    let numberOfVoters = parseInt(prompt("\nEnter number of voters: "));

    let voterNames = new Array(numberOfVoters);
    let voterAges = new Array(numberOfVoters);
    let voterIDs = new Array(numberOfVoters);
    let voterHasVoted = new Array(numberOfVoters).fill(false);

    for (let i = 0; i < numberOfVoters; i++) {
        voterNames[i] = prompt(`Enter name of voter ${i + 1}: `);

        let voterAge;
        do {
            voterAge = parseInt(prompt(`Enter age of voter ${i + 1}: `));
            if (voterAge < 18) console.log("Voter must be at least 18 years old.");
        } while (voterAge < 18);
        voterAges[i] = voterAge;

        let voterID;
        let idExists;
        do {
            voterID = prompt(`Enter unique ID of voter ${i + 1}: `);
            idExists = voterIDs.some(id => id && id.toLowerCase() === voterID.toLowerCase());
            if (idExists) console.log("ID already exists. Try again.");
        } while (idExists);
        voterIDs[i] = voterID;
    }

    console.log("\n==== Voting Process ====");
    for (let i = 0; i < numberOfVoters; i++) {
        if (!voterHasVoted[i]) {
            console.log(`\nHello, ${voterNames[i]} (Age: ${voterAges[i]}, ID: ${voterIDs[i]})`);
            console.log("Please cast your vote:");
            for (let j = 0; j < numberOfCandidates; j++) {
                console.log(`${j + 1}. ${candidateNames[j]}`);
            }

            let chosenCandidate;
            do {
                chosenCandidate = parseInt(prompt("Enter candidate number: "));
            } while (chosenCandidate < 1 || chosenCandidate > numberOfCandidates);

            candidateVotes[chosenCandidate - 1]++;
            voterHasVoted[i] = true;
            console.log("Vote cast successfully!");
        }
    }

    for (let i = 0; i < numberOfCandidates - 1; i++) {
        for (let j = i + 1; j < numberOfCandidates; j++) {
            if (candidateVotes[j] > candidateVotes[i]) {
                [candidateVotes[i], candidateVotes[j]] = [candidateVotes[j], candidateVotes[i]];
                [candidateNames[i], candidateNames[j]] = [candidateNames[j], candidateNames[i]];
            }
        }
    }

    console.log("\n==== Election Results ====");
    let highestVotes = candidateVotes[0];
    let tie = false;
    for (let i = 0; i < numberOfCandidates; i++) {
        console.log(`${candidateNames[i]} got ${candidateVotes[i]} votes.`);
        if (i > 0 && candidateVotes[i] === highestVotes) tie = true;
    }

    if (tie) {
        console.log("\nIt's a tie! No clear winner.");
    } else {
        console.log(`\nWinner: ${candidateNames[0]}`);
    }

    let continueElection = prompt("\nDo you want to run another election? (yes/no): ");
    if (continueElection.toLowerCase() !== "yes") break;
}
