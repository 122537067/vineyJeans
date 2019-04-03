package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.Comment;
import com.hwx.viney.mapper.CommentMapper;
import com.hwx.viney.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
