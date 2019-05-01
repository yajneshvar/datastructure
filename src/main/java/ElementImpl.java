import java.time.LocalDateTime;

public class ElementImpl implements Element {
    private String ticker;
    private Double value;
    private LocalDateTime time;


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ElementImpl(String ticker, Double value) {
        this.ticker = ticker;
        this.value = value;
        time = LocalDateTime.now();
    }

}