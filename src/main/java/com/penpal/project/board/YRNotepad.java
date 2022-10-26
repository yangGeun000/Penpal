package com.penpal.project.board;

public class YRNotepad {
	// git commit --amend 최근 커밋 수정 
	// :wq commit 상세설명 저장
	// git remote add origin http:~
	// git fetch --all써서 브런치 정보 업데이트 해주기
	// git branch로 확인하기
	// git checkout yuran	<==
	// git merge (받아올 브런치)yuran <==
	// git stash <== 임시저장
	// git checkout -t origin/yuran
	// < 아래 두 개 중 하나 설정해주면 git push로 push 가능 >
	// $ git push -u <원격 저장소명> <로컬 브랜치명>
	// $ git push --set-upstream <원격 저장소명> <로컬 브랜치명> 
	
	/* < 로컬파일 변경 후 루틴 >
	 * git status 				<== 빨강이면 반영 안된것
	 * git add (. or 파일명)		<== 파일 반영
	 * git commit -m "message" 	<== 커밋
	 * git push origin yuran	<== 내 커밋된 로컬파일 remote브런치로 전송
	 *
	 * < dev 반영 루틴 >
	 * git merge --squash 가져오는브런치이름
	 * 충돌해결
	 * git add 파일이름
	 * git commit -m "message"
	 * git push origin dev
	 * 
	 * */
}
