package com.ecsolutions.cadastros.persistence.specifications;


import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.persistence.entities.User;
import com.ecsolutions.cadastros.persistence.entities.User_;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UserSpecification {

    public static Specification<User> likeNome(String name) {
        return (root, query, builder) -> {
            if (StringUtils.isNotBlank(name)) {
                return builder.like(
                        builder.upper(root.get(User_.USERNAME)), "%%%s%%".formatted(name).toUpperCase()
                );
            }
            return null;
        };
    }

    public static Specification<User> likeEmail(String email) {
        return (root, query, builder) -> {
            if (StringUtils.isNotBlank(email)) {
                return builder.like(
                        builder.upper(root.get(User_.EMAIL)), "%%%s%%".formatted(email).toUpperCase()
                );
            }
            return null;
        };
    }

    public static Specification<User> status(SimpleStatusEnum status) {
        return (root, query, builder) -> {
            if (status != null) {
                return builder.equal(root.get(User_.STATUS), status);
            }
            return null;
        };
    }
}
