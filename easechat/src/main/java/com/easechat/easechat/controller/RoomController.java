package com.easechat.easechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easechat.easechat.common.api.R;
import com.easechat.easechat.common.utils.AuthUtil;
import com.easechat.easechat.entity.Room;
import com.easechat.easechat.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    private RoomMapper mapper;

    @GetMapping("/self/list")
    public R<List<Room>> selfList() {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getAccount, AuthUtil.getUserAccount());
        List<Room> list = mapper.selectList(wrapper);
        return R.data(list);
    }

    @GetMapping("/list")
    public R<List<Room>> list() {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        List<Room> list = mapper.selectList(wrapper);
        return R.data(list);
    }

    @PostMapping("/delete")
    public R<Void> userDelete(@RequestBody Room request) {
        Integer id = request.getId();
        if (id == null) {
            return R.fail("Room ID is required");
        }
        
        Room room = mapper.selectById(id);
        if (room == null) {
            return R.fail("Chat room not found");
        }
        if (!AuthUtil.getUserAccount().equals(room.getAccount())) {
            return R.fail("Unauthorized to delete this chat room");
        }
        mapper.deleteById(id);
        return R.success("Operation successful");
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody Room room) {
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            return R.fail("Room name cannot be empty");
        }
        
        room.setId(null);
        room.setAccount(AuthUtil.getUserAccount());
        mapper.insert(room);
        return R.success("Operation successful");
    }
}
