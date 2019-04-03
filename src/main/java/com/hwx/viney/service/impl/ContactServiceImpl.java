package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.Contact;
import com.hwx.viney.mapper.ContactMapper;
import com.hwx.viney.service.ContactService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

}
