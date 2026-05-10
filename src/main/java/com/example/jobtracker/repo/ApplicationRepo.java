package com.example.jobtracker.repo;

import com.example.jobtracker.model.ApplicationEntry;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationRepo {
	// グループ別の応募データを保持する　Map
	private final ConcurrentMap<String, ConcurrentMap<Long,ApplicationEntry>> store = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, AtomicLong> seqs = new ConcurrentHashMap<>();
	
	// グループごとの箱を取得（無ければ新しく作る）
	private ConcurrentMap<Long,ApplicationEntry> bucket(String group){
		return store.computeIfAbsent(group, g -> new ConcurrentHashMap<>());
	}
	// グループごとのIDカウンター
	private AtomicLong seq(String group) {
		return seqs.computeIfAbsent(group, g -> new AtomicLong(1));
	}
	// 一覧取得
	public List<ApplicationEntry> list(String group){
	    var all = new ArrayList<>(bucket(group).values());
	    all.sort(Comparator.comparing(
	        (ApplicationEntry e) -> e.nextActionAt == null ? Long.MAX_VALUE : e.nextActionAt
	    ));
	    return all;
	}
	 public List<ApplicationEntry> listAll(){
		 var out = new ArrayList<ApplicationEntry>();
		
		 for(var m : store.values()) {
			 out.addAll(m.values());
		 }
	 
	 
	 out.sort(Comparator.comparing(
			 (ApplicationEntry e) -> e.nextActionAt == null ? Long.MAX_VALUE : e.nextActionAt));
	return out;
	 }
	// 追加
	public long add(String group, ApplicationEntry e) {
		long id = seq(group).getAndIncrement();
		e.id = id;
		e.group = group;
		bucket(group).put(id, e);
		return id;
	}
	//　更新
	public void update(String group, ApplicationEntry e) {
		e.group = group;
		bucket(group).put(e.id, e);
	}
	// 削除
	public void delete(String group,long id) { bucket(group).remove(id);}
	// 検索
	public ApplicationEntry find(String group, long id) {return bucket(group).get(id);}
	
}
