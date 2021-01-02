import java.util.HashSet;
import java.util.Set;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {
	public double p_graph_;
	public double p_malicious_;
	public double p_txDistribution_;
	public int numRounds_;
	public int currRound_;
	boolean[] followees_;
	public Set<Transaction> txns_;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        // IMPLEMENT THIS
    	p_graph_ = p_graph;
    	p_malicious_ = p_malicious;
    	p_txDistribution_ = p_txDistribution;
    	numRounds_ = numRounds;
    	currRound_ = 0;
    }

    public void setFollowees(boolean[] followees) {
        // IMPLEMENT THIS
    	followees_ = followees;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
    	txns_ = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
    	++currRound_;
    	if (currRound_ == numRounds_)
    	{
    		boolean noOneTrusted = true;
    		for (boolean f : followees_)
    		{
    			if (f)
    			{
    				noOneTrusted = false;
    				break;
    			}
    		}
    		if (noOneTrusted)
    		{
    			Set<Transaction> retSet = new HashSet<Transaction>();
    			return retSet;
    		}
    	}
    	return txns_;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
    	if (currRound_ >= 2)
    		return;
    	Set<Integer> senders = new HashSet<Integer>();
    	for (Candidate candidate : candidates)
    	{
    		if (!followees_[candidate.sender])
    			continue;
    		senders.add(candidate.sender);
    		txns_.add(candidate.tx);
    	}
    	for (int i=0; i<followees_.length; ++i)
    	{
    		if (followees_[i] && !senders.contains(i))
    			followees_[i] = false;
    	}
    }
}
