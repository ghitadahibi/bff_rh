package ma.pca.bff.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.pca.starter.web.dto.PageableResponse;

import java.util.List;

@Getter
@Setter
public class ExamplePageableResponse extends PageableResponse<ExampleDto> {

    @Builder
    public ExamplePageableResponse(final List<ExampleDto> data,
        final int pageSize, final int totalPages, final int pageIndex) {
        super(data, pageSize, totalPages, pageIndex);
    }
}
