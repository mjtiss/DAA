import java.util.*;

public class apanhamos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Parse input parameters
        int E = scanner.nextInt();  // Number of teams
        int S = scanner.nextInt();  // Number of submissions before blackout
        int D = scanner.nextInt();  // Minimum duration between submissions
        int B = scanner.nextInt();  // Blackout start time
        int V = scanner.nextInt();  // Team ID we're checking
        
        // Initialize data structures to track teams' progress
        Map<Integer, Team> teams = new HashMap<>();
        for (int i = 1; i <= E; i++) {
            teams.put(i, new Team(i));
        }
        
        // Parse submissions before blackout
        for (int i = 0; i < S; i++) {
            int teamId = scanner.nextInt();
            int time = scanner.nextInt();
            int problemId = scanner.nextInt();
            int result = scanner.nextInt();
            
            teams.get(teamId).processSubmission(problemId, time, result == 1);
        }
        
        // Parse team V's submissions during blackout
        int K = scanner.nextInt();
        int[] blackoutSubmissions = new int[K];
        for (int i = 0; i < K; i++) {
            blackoutSubmissions[i] = scanner.nextInt();
        }
        
        // Find the problem team V solved during blackout
        int missingProblem = findMissingProblem(teams.get(V));
        
        // Process team V's blackout submissions
        for (int i = 0; i < K - 1; i++) {
            teams.get(V).processSubmission(missingProblem, blackoutSubmissions[i], false);
        }
        // Last submission was successful
        teams.get(V).processSubmission(missingProblem, blackoutSubmissions[K-1], true);
        
        // Determine if team V is definitely a winner
        boolean canWin = isGuaranteedWinner(teams, V, B, D);
        
        System.out.println(canWin ? "Vencemos" : "Nao sabemos");
        
        scanner.close();
    }
    
    private static int findMissingProblem(Team team) {
        for (int i = 1; i <= 7; i++) {
            if (!team.hasSolved(i)) {
                return i;
            }
        }
        return -1; // Should never happen as per problem statement
    }
    
    private static boolean isGuaranteedWinner(Map<Integer, Team> teams, int teamV, int blackoutStart, int minInterval) {
        Team vTeam = teams.get(teamV);
        int vSolvedCount = vTeam.getSolvedCount(); // Should be 7 as per problem statement
        int vTotalTime = vTeam.getTotalTime();
        
        // Check each team
        for (int teamId : teams.keySet()) {
            if (teamId == teamV) continue;
            
            Team team = teams.get(teamId);
            int solvedCount = team.getSolvedCount();
            
            // For each unsolved problem, check if this team could solve it
            Set<Integer> unsolvedProblems = team.getUnsolvedProblems();
            
            // If team can't solve all problems, they can't win
            if (unsolvedProblems.size() + solvedCount < vSolvedCount) {
                continue;
            }
            
            // Calculate when this team can next submit after blackout
            int nextTime = Math.max(blackoutStart, team.getLastSubmissionTime() + minInterval);
            
            // Simulate the best possible outcome - solving all remaining problems with optimal timing
            List<Integer> submissionTimes = new ArrayList<>();
            int currentTime = nextTime;
            
            // Calculate times for potential submissions
            while (currentTime <= 14400 && submissionTimes.size() < unsolvedProblems.size()) {
                submissionTimes.add(currentTime);
                currentTime += minInterval;
            }
            
            // If they can solve exactly as many as team V (7)
            if (solvedCount + submissionTimes.size() >= vSolvedCount) {
                // Calculate the best possible time
                int totalTime = team.getTotalTime();
                
                // Take the best possible times for remaining submissions
                // Order is important - submit solutions in order of times
                int needed = vSolvedCount - solvedCount;
                for (int i = 0; i < needed; i++) {
                    if (i < submissionTimes.size()) {
                        totalTime += submissionTimes.get(i);
                    }
                }
                
                // If they could potentially have a better or equal time, team V doesn't know if they won
                if (totalTime <= vTotalTime) {
                    return false;
                }
            }
        }
        
        // If no team can possibly tie or beat team V, then V is guaranteed to win
        return true;
    }
    
    static class Team {
        private int id;
        private Map<Integer, Problem> problems;
        private int lastSubmissionTime;
        
        public Team(int id) {
            this.id = id;
            this.problems = new HashMap<>();
            this.lastSubmissionTime = 0;
            for (int i = 1; i <= 7; i++) {
                problems.put(i, new Problem(i));
            }
        }
        
        public void processSubmission(int problemId, int time, boolean accepted) {
            Problem problem = problems.get(problemId);
            
            // Only process if the problem hasn't been solved yet
            if (!problem.isSolved()) {
                if (accepted) {
                    problem.solve(time);
                } else {
                    problem.addPenalty();
                }
            }
            
            lastSubmissionTime = Math.max(lastSubmissionTime, time);
        }
        
        public boolean hasSolved(int problemId) {
            return problems.get(problemId).isSolved();
        }
        
        public int getSolvedCount() {
            int count = 0;
            for (Problem problem : problems.values()) {
                if (problem.isSolved()) {
                    count++;
                }
            }
            return count;
        }
        
        public Set<Integer> getUnsolvedProblems() {
            Set<Integer> unsolved = new HashSet<>();
            for (int i = 1; i <= 7; i++) {
                if (!problems.get(i).isSolved()) {
                    unsolved.add(i);
                }
            }
            return unsolved;
        }
        
        public int getTotalTime() {
            int total = 0;
            for (Problem problem : problems.values()) {
                if (problem.isSolved()) {
                    total += problem.getTime();
                }
            }
            return total;
        }
        
        public int getLastSubmissionTime() {
            return lastSubmissionTime;
        }
    }
    
    static class Problem {
        private int id;
        private boolean solved;
        private int solvedTime;
        private int penalties;
        
        public Problem(int id) {
            this.id = id;
            this.solved = false;
            this.solvedTime = 0;
            this.penalties = 0;
        }
        
        public void solve(int time) {
            this.solved = true;
            this.solvedTime = time;
        }
        
        public void addPenalty() {
            penalties++;
        }
        
        public boolean isSolved() {
            return solved;
        }
        
        public int getTime() {
            if (!solved) return 0;
            // Time in seconds + 20 minutes (1200 seconds) penalty for each incorrect submission
            return solvedTime + (penalties * 1200);
        }
    }
}