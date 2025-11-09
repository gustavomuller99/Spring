package sia;


import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Name is required, maximum 255 characters."
    )
    private String name;

    @Future
    private Date auctionEnd;

//    private Set<Bid> bids = new HashSet<>();
//
//    public Set<Bid> getBids() {
//        return Collections.unmodifiableSet(bids);
//    }
//
//    public void addBid(Bid bid) {
//        if (bid == null)
//            throw new NullPointerException("Can't add null Bid");
//
//        if (bid.getItem() != null)
//            throw new IllegalStateException("Bid is already assigned to an Item");
//
//        bids.add(bid);
//        bid.setItem(this);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Date auctionEnd) {
        this.auctionEnd = auctionEnd;
    }
}
