package hello.myjdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    void uncheckedCatch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void uncheckThrow() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /**
     *  RuntimeException을 상속받으면 UncheckedException임
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * Unchecked 예외는
     * 예외를 잡거나, 던지지 않아도 됨
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.!!
     */
    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }

    static class Service {
        Repository repository = new Repository();
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                // 예외 처리 로직
                log.info("예외 처리 message={}", e.getMessage(), e);
            }
        }


        /**
         * 예외를 잡지 않으면 자연스럽게 상위로 던짐. 굳이 예외를 던지는 선언을 할 필요 없음
         */
        public void callThrow() {
            repository.call();
        }
    }


}
