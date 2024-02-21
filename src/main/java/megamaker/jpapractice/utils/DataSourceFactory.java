package megamaker.jpapractice.utils;

import megamaker.jpapractice.repository.DBUtils;
import megamaker.jpapractice.repository.MemberRepository;

public class DataSourceFactory {
    public DBUtils dbUtils() {
        return new DBUtils();
    }
    public MemberRepository memberRepository() {
        return new MemberRepository(dbUtils());
    }
}
