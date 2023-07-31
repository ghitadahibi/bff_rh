package ma.pca.bff.mapper;

import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.model.entities.Example;
import ma.pca.starter.web.utils.DateSource;
import ma.pca.starter.web.utils.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Mapper
public interface ExampleMapper {

    ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);

    @Mapping(source = "key", target = "name")
    @Mapping(source = "key", target = "createdAt", qualifiedByName = "toDate")
    Example mapExampleRequestToExample(ExampleRequest exampleRequest);

    @Named("toDate")
    default Date toDate(final String value) {
        return DateUtil.convertToDate(DateSource.date().newDateTime()
            .plus(Long.parseLong(value), ChronoUnit.DAYS));
    }
}