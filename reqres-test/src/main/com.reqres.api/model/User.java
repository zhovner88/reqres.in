import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated("com.robohorse.robopojogenerator")
public class User{

	@JsonProperty("name")
	private String name;

	@JsonProperty("job")
	private String job;

}