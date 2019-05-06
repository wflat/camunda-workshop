package de.mathema.springboot.camunda.workshop.jms.message;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.MessageHeaders;

/**
 * JmsMessageHeadersUtils.
 *
 * @author wflat
 */
public final class JmsMessageHeadersUtils {

    /**
     * @param messageHeaders
     * @return
     */
    public static String getMessageId(final MessageHeaders messageHeaders) {
        return get(messageHeaders, JmsHeaders.MESSAGE_ID);
    }

    /**
     * @param messageHeaders
     * @return
     */
    public static String getCorrelationId(final MessageHeaders messageHeaders) {
        return get(messageHeaders, JmsHeaders.CORRELATION_ID);
    }

    /**
     * @param messageHeaders
     * @param messageHeaderKey
     * @return The MessageHeader value. Or null
     */
    public static String get(final MessageHeaders messageHeaders, final String messageHeaderKey) {
        if (StringUtils.isEmpty(messageHeaderKey)) {
            return null;
        }
        return Optional.ofNullable(messageHeaders).map(mh -> mh.get(messageHeaderKey)).filter(String.class::isInstance)
                .map(String.class::cast).filter(StringUtils::isNotEmpty).orElse(null);
    }

    private JmsMessageHeadersUtils() {
    }
}
