-- Removes the examples (;") from the definitions
UPDATE synset_def SET def = (
	SELECT replace(def, coalesce(defagg, ''), '') newdef FROM synset_def sd 
	LEFT JOIN (
	
		SELECT synset, GROUP_CONCAT(engdef, '') defagg
		FROM (
		   SELECT *, '; "' || exeng.def ||'"' engdef from synset_ex exeng where exeng.lang = 'eng' order by exeng.sid
		) GROUP BY synset
	
	) da on (sd.synset = da.synset) where sd.synset = synset_def.synset AND sd.lang = synset_def.lang AND sd.def = synset_def.def AND sd.sid = synset_def.sid group by  sd.synset, sd.lang, sd.def, sd.sid
)

-- Removes the duplicates in the definitions
delete from synset_def
where    rowid not in
         (
         select  min(rowid)
         from    synset_def
         group by
                 def, lang, synset, sid
         );
-- Delete definitions that are substrings of another         
delete from synset_def where synset || def || lang || sid in (
	select b.synset || b.def || b.lang || b.sid from synset_def a, synset_def b where a.synset = b.synset and a.lang = b.lang and (a.def like '%'||b.def||'_%' or a.def like '%_'||b.def||'%')
)
-- Removes the tables not used by the library
drop table ancestor;
drop table link_def;
drop table pos_def;
drop table synlink;
drop table variant;
drop table xlink;
vacuum;