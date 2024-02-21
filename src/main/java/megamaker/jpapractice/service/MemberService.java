package megamaker.jpapractice.service;

import megamaker.jpapractice.repository.MemberRepository;
import megamaker.jpapractice.utils.DataSourceFactory;

public class MemberService {
    MemberRepository memberRepository = new DataSourceFactory().memberRepository();
}
