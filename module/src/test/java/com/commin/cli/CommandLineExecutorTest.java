package com.commin.cli;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class CommandLineExecutorTest {

    /**
     * diagnostic test
     * 
     * @args
     * 
     */
    @Test
    public void diagnostic_test() {
        boolean result = CommandLineExecutor.execute(
                "transporter.cmd -m diagnostic -u danalmusic@danalenter.co.kr  -p ixso-spww-vant-omuc -v eXtreme");
        assertTrue(result);
    }

    /**
     * queryTicket test
     * 
     * Query Tickets 모드를 사용하여 아직 해결되지 않은 iTunes 티켓에 대한 정보를 검색합니다. iTunes Connect 계정이
     * 있는 경우에만 Query Tickets 모드를 사용할 수 있습니다. 인코딩 스튜디오사용자 이름과 비밀번호로 Query Ticket을 사용할
     * 수 없습니다.
     * 
     * @args
     * 
     */
    @Test
    public void queryTicket_test() {
        boolean result = CommandLineExecutor.execute(
                "transporter.cmd -m queryTickets -u danalmusic@danalenter.co.kr  -p ixso-spww-vant-omuc -vendor_id 8809838633884 -v eXtreme");
        assertTrue(result);
    }

    /**
     * status test
     * 
     * Status 모드를 사용하여 한 개 이상의 지정된 공급업체 (-vendor_id 또는 -vendor_ids) 또는 Apple 식별자
     * (-apple_id 또는 -apple_ids)의 최근 업로드된 패키지, 또는 모든 패키지의 상태 정보를 검색할 수 있습니다.
     * 
     * Transporter의 Status 모드를 여러 식별자에서 실행할 수 있습니다. 가장 최근에 업로드 한 패키지 (-m status) 또는
     * 모든 업로드 된 패키지 (-m statusAll)의 상태 정보를 요청할 때 지정할 수 있는 공급 업체 또는 Apple 식별자의 수에는
     * 제한이 없습니다
     * 
     * @args
     * 
     */
    @Test
    public void status_test() {

        boolean result = CommandLineExecutor.execute(
                "transporter -m status -u danalmusic@danalenter.co.kr  -p ixso-spww-vant-omuc -vendor_id 8809838633884 -t Aspera");

        assertTrue(result);
    }

    @Test
    public void args_test() {
        String command = "transporter";
        Map<String, String> argMap = new HashMap<>();
        argMap.put("-m", "status");
        argMap.put("-u", "danalmusic@danalenter.co.kr");
        argMap.put("-p", "ixso-spww-vant-omuc");
        argMap.put("-vendor_id", "8809838633884");
        argMap.put("-t", "Aspera");

        boolean result = CommandLineExecutor.execute(command, argMap);
        assertTrue(result);
    }

}
